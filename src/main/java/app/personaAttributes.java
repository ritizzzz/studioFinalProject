package app;

public class personaAttributes {
        private int id;
        private String attributeType;
        private String desc;
        public personaAttributes(int id, String attributeType, String desc){
            this.id = id;
            this.attributeType = attributeType;
            this.desc = desc;
        }

        public int getId(){
            return id;
        }

        public String getAttributeType(){
            return attributeType;
        }

        public String getDesc(){
            return desc;
        }
}
