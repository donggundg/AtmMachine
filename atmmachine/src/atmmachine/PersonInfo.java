package atmmachine;

//Represents personal information of an individual, encapsulating attributes like name and date of birth.
public class PersonInfo {
  private String name;// variable to store the name of the person.
  private String dateOfBirth;// varaible to store the date of birth of the person.

  //constructor
  public PersonInfo(String name, String dateOfBirth) {
      this.name = name;
      this.dateOfBirth = dateOfBirth;
  }

  //getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
  
}
