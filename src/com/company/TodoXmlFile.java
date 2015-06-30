package com.company;


import jdk.internal.org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created by root on 30/6/15.
 */
public class TodoXmlFile {


    private static TodoList _createTodoListFromXml(Document xmlDocument){
        TodoList todoList = new TodoList();

        Element listElement = xmlDocument.getDocumentElement();

        if (listElement == null){
            return todoList;
        }

        NodeList todoNodes = listElement.getElementsByTagName("Todo");

        for (int i = 0; i < todoNodes.getLength() ; i++) {
            Node todoNode = todoNodes.item(i);
            todoList.addItem(_createTodoFromXmlElement(((Element) todoNode)));

        }
        return todoList;
    }

    private static Item _createTodoFromXmlElement(Element e){
        String description = e.getElementsByTagName("Description").item(0).getTextContent();
        NodeList categoryNodes = e.getElementsByTagName("Category");
        String[] categories = new String[categoryNodes.getLength()];
        for (int i = 0; i < categories.length; i++) {
            categories[i] = categoryNodes.item(i).getTextContent();
        }
        return  new Item(description, categories);
    }

    private static Document _createXmlFromTodoList(TodoList todoList) throws ParserConfigurationException{
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document dom = documentBuilder.newDocument();
        Element rootElement = dom.createElement("Todos");

        for(Item item:todoList){
            Element itemElement = _createXmlElementFromTodo(dom, item);
            rootElement.appendChild(itemElement);
        }
        dom.appendChild(rootElement);
        return dom;
    }

    private static Element _createXmlElementFromTodo(Document dom, Item item){
        Element todoElement = dom.createElement("Todo");
        Element description = dom.createElement("Description");
        description.appendChild(dom.createTextNode(item.get_description()));
        todoElement.appendChild(description);

        for(String c:item.get_categories()){
            Element category = dom.createElement("Category");
            category.appendChild(dom.createTextNode(c));
            todoElement.appendChild(category);
        }
        return  todoElement;
    }

    public static TodoList readTodoListFromXml(String file) throws ParserConfigurationException, org.xml.sax.SAXException{
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document dom;
        try {
            dom = documentBuilder.parse(file);

        }
        catch (IOException ioe){
            dom = documentBuilder.newDocument();
        }

        return _createTodoListFromXml(dom);

    }

    public static void saveTodoListToXml(TodoList todoList, String file) throws TransformerConfigurationException, ParserConfigurationException, FileNotFoundException, TransformerException{
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.transform(new DOMSource(_createXmlFromTodoList(todoList)), new StreamResult(new FileOutputStream(file)));
    }

}
