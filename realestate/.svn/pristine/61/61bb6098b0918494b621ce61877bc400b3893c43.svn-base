package cn.gtmap.realestate.common.core.qo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

public class BaseQO {

    private List<String> ids;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "BaseQO{" +
                "ids=" + ids +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseQO)){
            return false;
        }
        BaseQO baseQO = (BaseQO) o;
        return Objects.equals(getIds(), baseQO.getIds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIds());
    }
}
