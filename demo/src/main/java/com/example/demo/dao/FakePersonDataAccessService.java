package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Person;

@Repository("fakeDAO")
public class FakePersonDataAccessService implements PersonDAO {
	
	private static List<Person> DB = new ArrayList<Person>();

	@Override
	public int insertPerson(UUID id, Person person) {
		DB.add(new Person(id, person.getName()));
		return 1;
	}

	@Override
	public List<Person> selectAllPeople() {
		return DB;
	}

	@Override
	public int deletePersonByID(UUID id) {
		Optional<Person> personMaybe = selectPersonById(id);
		if (personMaybe.isEmpty())
			return 0;
		DB.remove(personMaybe.get());
		return 1;
	}

	@Override
	public int updatePersonByID(UUID id, Person person) {
		return selectPersonById(id) 
				.map(p -> {
					int indexOfPersonToUpdate = DB.indexOf(person);
					if (indexOfPersonToUpdate >= 0) {
						DB.set(indexOfPersonToUpdate, person);
						return 1;
					}
					return 0;
				})
				.orElse(0);
		
	}

	@Override
	public Optional<Person> selectPersonById(UUID id) {
		
		return DB.stream().filter(person -> person.getId().equals(id)).findFirst();
	}


}
