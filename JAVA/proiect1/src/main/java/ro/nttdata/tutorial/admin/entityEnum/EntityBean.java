package ro.nttdata.tutorial.admin.entityEnum;

public class EntityBean
{
    @Enum(enumClass=Entities.class, ignoreCase=true)
    String sample;

    public String getSample()
    {
        return sample;
    }

    public void setSample(String sample)
    {
        this.sample = sample;
    }

}