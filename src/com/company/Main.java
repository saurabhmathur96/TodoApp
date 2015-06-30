package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        TodoXmlFile todoXmlFile = new TodoXmlFile();
        try {
            TodoList todoList = todoXmlFile.readTodoListFromXml("dataFile.xml");
            System.out.println(todoList.inCategory("Food"));
            //todoXmlFile.saveTodoListToXml(todoList, "dataFile.xml");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
