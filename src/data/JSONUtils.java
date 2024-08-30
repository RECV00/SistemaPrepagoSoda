package data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import domain.Student;
import domain.Dishe;
import domain.Recharge;
public class JSONUtils<T> {

//	ruta
private final String filePath;
//manejo  de Json
private static final ObjectMapper mapper = new ObjectMapper()
		.registerModule(new JavaTimeModule());// se agrega si debo de usar localDate

//Constructor
public JSONUtils(String route) {
	this.filePath = route;
}

public void saveElement(T t)  throws IOException{
	List<T> temp = getElements((Class<T>)t.getClass());
	temp.add(t);
	mapper.writeValue(new File(filePath),temp);
}

public void deleteElement(T t, int index) throws IOException {
    List<T> temp = getElements((Class<T>) t.getClass());
    temp.remove(index); 
    mapper.writeValue(new File(filePath), temp);
}

public List<T> getElements(Class<T> temp) throws IOException{
	File file = new File(filePath);//Jva.io
	
	if(!file.exists()) {
		return new ArrayList<>();
	}
	return mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, temp));
}

public void updateElementStudent(T updatedElement, String carnet) throws IOException {
    List<T> elements = getElements((Class<T>) updatedElement.getClass());
    boolean found = false;
    for (int i = 0; i < elements.size(); i++) {
        T element = elements.get(i);
        if (((Student) element).getCarnetStudent().equalsIgnoreCase(carnet)) {
            elements.set(i, updatedElement);
            found = true;
            break;
        }
    }

    if (found) {
        mapper.writeValue(new File(filePath), elements);
    } else {
        throw new IOException("Element with ID " + carnet + " not found.");
    }
}

public void updateElementDishes(T updatedElement, String name) throws IOException {
    List<T> elements = getElements((Class<T>) updatedElement.getClass());
    boolean found = false;
    for (int i = 0; i < elements.size(); i++) {
        T element = elements.get(i);
        if (((Dishe) element).getServiceName().equalsIgnoreCase(name)) {
            elements.set(i, updatedElement);
            found = true;
            break;
        }
    }

    if (found) {
        mapper.writeValue(new File(filePath), elements);
    } else {
        throw new IOException("Element with ID " + name + " not found.");
    }
}
public void updateElementRecharges(T updatedElement, String carnet) throws IOException {
    List<T> elements = getElements((Class<T>) updatedElement.getClass());
    boolean found = false;
    for (int i = 0; i < elements.size(); i++) {
        T element = elements.get(i);
        if (((Recharge) element).getCarnetStudent().equalsIgnoreCase(carnet)) {
            elements.set(i, updatedElement);
            found = true;
            break;
        }
    }

    if (found) {
        mapper.writeValue(new File(filePath), elements);
    } else {
        throw new IOException("Element with ID " + carnet + " not found.");
    }
}
}
