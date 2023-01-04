package cn.gtmap.realestate.exchange.core.dto.wwsq.init.cfjfdj;

public class CreateData {

    private CfRequestDTO data;

    public CfRequestDTO getData() {
        return data;
    }

    public void setData(CfRequestDTO data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CreateDat{" +
                "data=" + data +
                '}';
    }
}
