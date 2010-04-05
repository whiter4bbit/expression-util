package info.whiter4bbit.common.util;

/**
 * DataTypes
 * Date: 29.03.2010
 */
public enum DataTypes {

    DOUBLE("double", Double.class),
    INTEGER("int", Double.class),
    LONG("long", Long.class),
    BOOLEAN("boolean", Boolean.class),
    STRING("string", String.class),
    NONE("none", Object.class);

    private String name;

    private Class<?> javaDataType;

    DataTypes(String name, Class<?> javaDataType) {
        this.name = name;
        this.javaDataType = javaDataType;
    }

    public String getTypeName(){
        return name;
    }

    public Class<?> getJavaDataType() {
        return javaDataType;
    }

    public static DataTypes getByClass(Class<?> clazz){
        for(DataTypes type : DataTypes.values()){
            if(type.getJavaDataType().equals(clazz)){
                return type;
            }
        }
        return DataTypes.NONE;
    }
}
