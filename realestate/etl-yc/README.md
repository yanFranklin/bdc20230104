## 创建security云应用——基于App starter的云应用骨架 ##

### 创建security云应用有以下两种方式。 ###
### 一、maven命令 ###

#### 1、dos进入新建项目的目标文件夹 ####

#### 2、输入 mvn archetype:generate 命令 ####

如下图，sec-cloud-app-creator-archetype即为基于security的云应用骨架。

![image11](https://i.imgur.com/2jpYqr0.png)

#### 3、按提示输入，红色标注为手动录入，白色标注使用默认值（即紧跟着冒号前面的值） ####

![image12](https://i.imgur.com/RFmHlfR.png)

#### 4、出现以下结果表示骨架已生成 ####

![image13](https://i.imgur.com/f4Qr7K1.png)

#### 5、骨架生成后，在可在目标文件夹中看到新生成的项目，用IntelliJ IDEA打开，如下图所示 ####

![image14](https://i.imgur.com/jiHI3UX.png)

### 二、IntelliJ IDEA自动生成 ###

#### 1、打开IntelliJ IDEA，file —> new —> project ####

#### 2、选择maven，勾选Create from archetype，点击Add Archetype ####

![image21](https://i.imgur.com/uFTyfs9.png)

#### 3、输入需下archetype的GroupId、ArtifactId和Version，点OK ####

![image22](https://i.imgur.com/vbo0ZPA.png)

#### 4、新增archetype后，列表中出现刚刚新增的archetype，选择，点next进入下一步 ####

![image23](https://i.imgur.com/uo59uEU.png)

#### 5、编辑新建项目的GroupId和ArtifactId和version信息，点next ####

![image24](https://i.imgur.com/ZnS3uLJ.png)

#### 6、选择maven路径，properties中核对新建项目和来源archetype的配置信息 ####

![image25](https://i.imgur.com/IcAJ0kW.png)

#### 7、输入项目名称，修改项目路径，点finish即开始生成archetype到新项目中 ####

![image26](https://i.imgur.com/etQMI5s.png)

#### 8、项目创建成功 ####

![image27](https://i.imgur.com/03ESe8d.png)

> <font face="微软雅黑" size=2>**注：如果想省略第二步创建第三方archetype的过程，可以直接进入intellij idea的archetype plugin路径（如下），找到UserArchetypes.xml配置，添加你自定义的archetype plugin配置。**
> （*c:\users\zenglihuan\ .intellijidea15\system\maven\indices*）</font>
>
![image28](https://i.imgur.com/UnvFGNA.png)

<br><br><br><br>
