/**
 * Copyright 2005-2013 Dozer Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.gtmap.realestate.common.core.dozer;

import org.dozer.*;
import org.dozer.cache.CacheManager;
import org.dozer.cache.DozerCacheManager;
import org.dozer.cache.DozerCacheType;
import org.dozer.classmap.ClassMappings;
import org.dozer.classmap.Configuration;
import org.dozer.classmap.MappingFileData;
import org.dozer.config.GlobalSettings;
import org.dozer.event.DozerEventManager;
import org.dozer.factory.DestBeanCreator;
import org.dozer.loader.CustomMappingsLoader;
import org.dozer.loader.LoadMappingsResult;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.xml.MappingFileReader;
import org.dozer.loader.xml.MappingStreamReader;
import org.dozer.loader.xml.XMLParserFactory;
import org.dozer.metadata.DozerMappingMetadata;
import org.dozer.metadata.MappingMetadata;
import org.dozer.stats.GlobalStatistics;
import org.dozer.stats.StatisticType;
import org.dozer.stats.StatisticsInterceptor;
import org.dozer.stats.StatisticsManager;
import org.dozer.util.MappingValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Public Dozer Mapper implementation. This should be used/defined as a singleton within your application. This class
 * performs several one-time initializations and loads the custom xml mappings, so you will not want to create many
 * instances of it for performance reasons. Typically a system will only have one DozerBeanMapper instance per VM. If
 * you are using an IOC framework (i.e Spring), define the Mapper as singleton="true". If you are not using an IOC
 * framework, a DozerBeanMapperSingletonWrapper convenience class has been provided in the Dozer jar.
 * <p/>
 * It is technically possible to have multiple DozerBeanMapper instances initialized, but it will hinder internal
 * performance optimizations such as caching.
 *
 * @author tierney.matt
 * @author garsombke.franz
 * @author dmitry.buzdin
 * @author suwarnaratana.arm
 */
public class DozerBeanMapper implements Mapper {

  private final Logger log = LoggerFactory.getLogger(DozerBeanMapper.class);

  private final StatisticsManager statsMgr = GlobalStatistics.getInstance().getStatsMgr();
  private final AtomicBoolean initializing = new AtomicBoolean(false);
  private final CountDownLatch ready = new CountDownLatch(1);

  /*
   * Accessible for custom injection
   */
  private final List<String> mappingFiles = new ArrayList<String>();
  private final List<CustomConverter> customConverters = new ArrayList<CustomConverter>();
  private final List<MappingFileData> builderMappings = new ArrayList<MappingFileData>();
  private final List<DozerEventListener> eventListeners = new ArrayList<DozerEventListener>();
  private final Map<String, CustomConverter> customConvertersWithId = new HashMap<String, CustomConverter>();

  private CustomFieldMapper customFieldMapper;

  /*
   * Not accessible for injection
   */
  private ClassMappings customMappings;
  private Configuration globalConfiguration;
  // There are no global caches. Caches are per bean mapper instance
  private final CacheManager cacheManager = new DozerCacheManager();
  private DozerEventManager eventManager;

  public DozerBeanMapper() {
    this(Collections.<String>emptyList());
  }

  public DozerBeanMapper(List<String> mappingFiles) {
    this.mappingFiles.addAll(mappingFiles);
    init();
  }

  /**
   * {@inheritDoc}
   */
  public void map(Object source, Object destination, String mapId) throws MappingException {
    getMappingProcessor().map(source, destination, mapId);
  }

  /**
   * {@inheritDoc}
   */
  public <T> T map(Object source, Class<T> destinationClass, String mapId) throws MappingException {
    return getMappingProcessor().map(source, destinationClass, mapId);
  }

  /**
   * {@inheritDoc}
   */
  public <T> T map(Object source, Class<T> destinationClass) throws MappingException {
    return getMappingProcessor().map(source, destinationClass);
  }

  /**
   * {@inheritDoc}
   */
  public void map(Object source, Object destination) throws MappingException {
    getMappingProcessor().map(source, destination);
  }

  public <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) throws MappingException{
    List<T> destinationList = new ArrayList<>();
    for (Object sourceObject : sourceList) {
      destinationList.add(getMappingProcessor().map(sourceObject, destinationClass));
    }
    return destinationList;
  }

  public <T> List<T> mapList(Collection sourceList, Class<T> destinationClass, String mapId) throws MappingException{
    List<T> destinationList = new ArrayList<>();
    for (Object sourceObject : sourceList) {
      destinationList.add(getMappingProcessor().map(sourceObject, destinationClass, mapId));
    }
    return destinationList;
  }

  /**
   * Returns list of provided mapping file URLs
   *
   * @return unmodifiable list of mapping files
   */
  public List<String> getMappingFiles() {
    return Collections.unmodifiableList(mappingFiles);
  }

  /**
   * Sets list of URLs for custom XML mapping files, which are loaded when mapper gets initialized.
   * It is possible to load files from file system via file: prefix. If no prefix is given mapping files are
   * loaded from classpath and can be packaged along with the application.
   *
   * @param mappingFileUrls URLs referencing custom mapping files
   * @see URL
   */
  public void setMappingFiles(List<String> mappingFileUrls) {
    checkIfInitialized();
    this.mappingFiles.clear();
    this.mappingFiles.addAll(mappingFileUrls);
  }

  public void setFactories(Map<String, BeanFactory> factories) {
    checkIfInitialized();
    DestBeanCreator.setStoredFactories(factories);
  }

  public void setCustomConverters(List<CustomConverter> customConverters) {
    checkIfInitialized();
    this.customConverters.clear();
    this.customConverters.addAll(customConverters);
  }

  public List<CustomConverter> getCustomConverters() {
    return Collections.unmodifiableList(customConverters);
  }

  public Map<String, CustomConverter> getCustomConvertersWithId() {
    return Collections.unmodifiableMap(customConvertersWithId);
  }

  private void init() {
    DozerInitializer.getInstance().init();

    log.debug("Initializing a new instance of dozer bean mapper.");

    // initialize any bean mapper caches. These caches are only visible to the bean mapper instance and
    // are not shared across the VM.
    GlobalSettings globalSettings = GlobalSettings.getInstance();
    cacheManager.addCache(DozerCacheType.CONVERTER_BY_DEST_TYPE.name(), globalSettings.getConverterByDestTypeCacheMaxSize());
    cacheManager.addCache(DozerCacheType.SUPER_TYPE_CHECK.name(), globalSettings.getSuperTypesCacheMaxSize());

    // stats
    statsMgr.increment(StatisticType.MAPPER_INSTANCES_COUNT);
  }

  public void destroy() {
    DozerInitializer.getInstance().destroy();
  }

  protected Mapper getMappingProcessor() {
    initMappings();

    Mapper processor = new MappingProcessor(customMappings, globalConfiguration, cacheManager, statsMgr, customConverters,
            eventManager, getCustomFieldMapper(), customConvertersWithId);

    // If statistics are enabled, then Proxy the processor with a statistics interceptor
    if (statsMgr.isStatisticsEnabled()) {
      processor = (Mapper) Proxy.newProxyInstance(processor.getClass().getClassLoader(),
              processor.getClass().getInterfaces(),
              new StatisticsInterceptor(processor, statsMgr));
    }

    return processor;
  }

  void loadCustomMappings() {
    CustomMappingsLoader customMappingsLoader = new CustomMappingsLoader();
    List<MappingFileData> xmlMappings = loadFromFiles(mappingFiles);
    ArrayList<MappingFileData> allMappings = new ArrayList<MappingFileData>();
    allMappings.addAll(xmlMappings);
    allMappings.addAll(builderMappings);
    LoadMappingsResult loadMappingsResult = customMappingsLoader.load(allMappings);
    this.customMappings = loadMappingsResult.getCustomMappings();
    this.globalConfiguration = loadMappingsResult.getGlobalConfiguration();
  }

  private List<MappingFileData> loadFromFiles(List<String> mappingFiles) {
    MappingFileReader mappingFileReader = new MappingFileReader(XMLParserFactory.getInstance());
    List<MappingFileData> mappingFileDataList = new ArrayList<MappingFileData>();
    if (mappingFiles != null && mappingFiles.size() > 0) {
      log.debug("Using the following xml files to load custom mappings for the bean mapper instance: {}", mappingFiles);
      for (String mappingFileName : mappingFiles) {
        URL url = MappingValidator.validateURL(mappingFileName);
        MappingFileData mappingFileData = mappingFileReader.read(url);

        mappingFileDataList.add(mappingFileData);
      }
    }
    return mappingFileDataList;
  }

  /**
   * Add mapping XML from InputStream resources for mapping not stored in
   * files (e.g. from database.) The InputStream will be read immediately to
   * internally create MappingFileData objects so that the InputStreams may be
   * closed after the call to this method.
   *
	 * @param xmlStream Dozer mapping XML InputStream
	 */
	public void addMapping(InputStream xmlStream) {
    checkIfInitialized();
    MappingStreamReader fileReader = new MappingStreamReader(XMLParserFactory.getInstance());
    MappingFileData mappingFileData = fileReader.read(xmlStream);
    builderMappings.add(mappingFileData);
  }

  /**
   * Adds API mapping to given mapper instance.
   *
   * @param mappingBuilder mappings to be added
   */
  public void addMapping(BeanMappingBuilder mappingBuilder) {
    checkIfInitialized();
    MappingFileData mappingFileData = mappingBuilder.build();
    builderMappings.add(mappingFileData);
  }

  public List<? extends DozerEventListener> getEventListeners() {
    return Collections.unmodifiableList(eventListeners);
  }

  public void setEventListeners(List<? extends DozerEventListener> eventListeners) {
    checkIfInitialized();
    this.eventListeners.clear();
    this.eventListeners.addAll(eventListeners);
  }

  public CustomFieldMapper getCustomFieldMapper() {
    return customFieldMapper;
  }

  public void setCustomFieldMapper(CustomFieldMapper customFieldMapper) {
    checkIfInitialized();
    this.customFieldMapper = customFieldMapper;
  }

  /**
   * The {@link MappingMetadata} interface can be used to query information about the current
   * mapping definitions. It provides read only access to all important classes and field
   * mapping properties. When first called, initializes all mappings if map() has not yet been called.
   *
   * @return An instance of {@line org.dozer.metadata.MappingMetadata} which serves starting point 
   * for querying mapping information. 
   */
  public MappingMetadata getMappingMetadata() {
    initMappings();
    return new DozerMappingMetadata(customMappings);
  }

  /**
   * Converters passed with this method could be further referenced in mappings via its unique id.
   * Converter instances passed that way are considered stateful and will not be initialized for each mapping.
   *
   * @param customConvertersWithId converter id to converter instance map
   */
  public void setCustomConvertersWithId(Map<String, CustomConverter> customConvertersWithId) {
    checkIfInitialized();
    this.customConvertersWithId.clear();
    this.customConvertersWithId.putAll(customConvertersWithId);
  }

  private void checkIfInitialized() {
    if (ready.getCount() == 0) {
      throw new MappingException("Dozer Bean Mapper is already initialized! Modify settings before calling map()");
    }
  }

  private void initMappings() {
    if (initializing.compareAndSet(false, true)) {
      try {
        loadCustomMappings();
        eventManager = new DozerEventManager(eventListeners);
      } catch (RuntimeException e) {
        // reset initialized state if error happens
        initializing.set(false);
        throw e;
      } finally {
        ready.countDown();
      }
    }

    try {
      ready.await();
    } catch (InterruptedException e) {
      log.error("Thread interrupted: ", e);
      // Restore the interrupted status:
      Thread.currentThread().interrupt();
    }
  }

}
