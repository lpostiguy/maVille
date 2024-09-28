package Prototype.User;

public class Intervenant extends User {

    // Constructor
    public Intervenant(String firstName, String lastName, String email,
                       String cityId, String entityType, int password) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setCityId(cityId);
        setEntityType(entityType);
        setPassword(password);
    }

    // Attributes
    private String cityId;
    private String entityType;

    // Getters
    public String getCityId() {
        return cityId;
    }

    public String getEntityType() {
        return entityType;
    }

    // Setters
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }
}
