package unnecessary;

import java.util.UUID;

public class metaobject {

    private UUID uuid;
    private String name;
    private String descripton;

    public metaobject(UUID uuid, String name, String descripton) {
        this.uuid = uuid;
        this.name = name;
        this.descripton = descripton;
    }

    public metaobject() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescripton() {
        return descripton;
    }

    public void setDescripton(String descripton) {
        this.descripton = descripton;
    }
}
