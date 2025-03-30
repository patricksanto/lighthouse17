package nl.saxion.re.sponsorrun.data;

public class Team {
    private String name;
    private String province;
    private String city;
    private int members;
    private String teacher;
    private String contact;

    public Team(String name, String province, String city, int members, String teacher, String contact) {
        this.name = name;
        this.province = province;
        this.city = city;
        this.members = members;
        this.teacher = teacher;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public int getMembers() {
        return members;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getContact() {
        return contact;
    }
}
