package com.github.mohammadmasoomi.inventory.core.entity.base;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 2560729798651041530L;

    @Id
    @SequenceGenerator(name = "idGenerator", sequenceName = "INVENTORY_ID_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INVENTORY_ID_SEQUENCE")
    private long id;

    @Version
    private long version;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
