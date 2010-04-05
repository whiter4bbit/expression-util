package info.whiter4bbit.common.util;

import java.util.Arrays;
import java.util.List;

/**
 * CommonDataTypes
 * Date: 05.04.2010
 */
public enum CommonDataTypes {
    
    BOOLEAN(DataTypes.BOOLEAN), NUMBER(DataTypes.LONG, DataTypes.DOUBLE), STRING(DataTypes.STRING), NONE();

    private List<DataTypes> dataTypes;

    CommonDataTypes(List<DataTypes> dataTypes) {
        this.dataTypes = dataTypes;
    }

    CommonDataTypes(DataTypes...dataTypes) {
        this.dataTypes = Arrays.asList(dataTypes);
    }

    public List<DataTypes> getDataTypes() {
        return dataTypes;
    }

    public static CommonDataTypes getByDataType(DataTypes dataTypes){
        for(CommonDataTypes commonType : CommonDataTypes.values()){
            if(commonType.getDataTypes().contains(dataTypes)){
                return commonType;
            }
        }
        return NONE;
    }
}
