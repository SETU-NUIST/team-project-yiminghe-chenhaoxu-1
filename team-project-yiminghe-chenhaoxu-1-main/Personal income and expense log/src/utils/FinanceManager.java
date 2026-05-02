package utils;

import models.Record;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FinanceManager {
    private final List<Record> records = new ArrayList<>();
    private String xmlPath;

    public FinanceManager(String userName) {
        xmlPath = userName + "_record.xml";
        loadRecordsFromXML();
    }

    public void addRecord(Record record) {
        records.add(record);
        saveRecordsToXML();
    }

    public boolean deleteRecord(int index) {
        if(index >=0 && index < records.size()){
            records.remove(index);
            saveRecordsToXML();
            return true;
        }
        return false;
    }

    public boolean updateRecord(int index, Record newRec) {
        if(index >=0 && index < records.size()){
            records.set(index, newRec);
            saveRecordsToXML();
            return true;
        }
        return false;
    }

    public List<Record> getAllRecords() {
        return new ArrayList<>(records);
    }

    public List<Record> getRecordsByDate(String date) {
        List<Record> res = new ArrayList<>();
        for(Record r : records){
            if(r.getDate().equals(date)){
                res.add(r);
            }
        }
        return res;
    }

    private void loadRecordsFromXML() {
        File file = new File(xmlPath);
        if(!file.exists()){
            return;
        }
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            NodeList list = doc.getElementsByTagName("record");
            for(int i=0;i<list.getLength();i++){
                Element e = (Element)list.item(i);
                String d = e.getElementsByTagName("date").item(0).getTextContent();
                String t = e.getElementsByTagName("type").item(0).getTextContent();
                double m = Double.parseDouble(e.getElementsByTagName("amount").item(0).getTextContent());
                String desc = e.getElementsByTagName("description").item(0).getTextContent();
                records.add(new Record(d,t,m,desc));
            }
        }catch (Exception e){
            System.out.println("读取数据失败");
        }
    }

    private void saveRecordsToXML() {
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            Element root = doc.createElement("records");
            doc.appendChild(root);

            for(Record r : records){
                Element rec = doc.createElement("record");

                Element date = doc.createElement("date");
                date.setTextContent(r.getDate());
                rec.appendChild(date);

                Element type = doc.createElement("type");
                type.setTextContent(r.getType());
                rec.appendChild(type);

                Element amount = doc.createElement("amount");
                amount.setTextContent(r.getAmount()+"");
                rec.appendChild(amount);

                Element desc = doc.createElement("description");
                desc.setTextContent(r.getDescription());
                rec.appendChild(desc);

                root.appendChild(rec);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer ts = tf.newTransformer();
            ts.setOutputProperty(OutputKeys.INDENT,"yes");
            ts.transform(new DOMSource(doc),new StreamResult(new File(xmlPath)));
        }catch (Exception e){
            System.out.println("保存数据失败");
        }
    }
}