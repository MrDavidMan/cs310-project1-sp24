package edu.jsu.mcis.cs310;

import com.github.cliftonlabs.json_simple.*;
import com.opencsv.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
    
public class ClassSchedule {
    
    private final String CSV_FILENAME = "jsu_sp24_v1.csv";
    private final String JSON_FILENAME = "jsu_sp24_v1.json";
    
    private final String CRN_COL_HEADER = "crn";
    private final String SUBJECT_COL_HEADER = "subject";
    private final String NUM_COL_HEADER = "num";
    private final String DESCRIPTION_COL_HEADER = "description";
    private final String SECTION_COL_HEADER = "section";
    private final String TYPE_COL_HEADER = "type";
    private final String CREDITS_COL_HEADER = "credits";
    private final String START_COL_HEADER = "start";
    private final String END_COL_HEADER = "end";
    private final String DAYS_COL_HEADER = "days";
    private final String WHERE_COL_HEADER = "where";
    private final String SCHEDULE_COL_HEADER = "schedule";
    private final String INSTRUCTOR_COL_HEADER = "instructor";
    private final String SUBJECTID_COL_HEADER = "subjectid";
    
    public String convertCsvToJsonString(List<String[]> csv) {

        JsonArray records = new JsonArray();
        

        
        
           
        Iterator<String[]> iterator = csv.iterator();
        LinkedHashMap<String, LinkedHashMap> schedulemap = new LinkedHashMap<>();        
        LinkedHashMap<String, String> schedule = new LinkedHashMap<>();
        
        LinkedHashMap<String, LinkedHashMap> subjectmap = new LinkedHashMap<>();
        LinkedHashMap<String, String> subject = new LinkedHashMap<>();
        
        LinkedHashMap<String, LinkedHashMap> coursemap = new LinkedHashMap<>();
        LinkedHashMap<String, LinkedHashMap> course = new LinkedHashMap<>(); 
        LinkedHashMap<String, String> coursevalues = new LinkedHashMap<>(); 
        
        LinkedHashMap<String, ArrayList> sectionmap = new LinkedHashMap<>();
        LinkedHashMap<String, String> sectionvalues = new LinkedHashMap<>();         
        LinkedHashMap<String, String[]> instructors = new LinkedHashMap<>();
        
        ArrayList<HashMap> sectionarray = new ArrayList<>();

        if (iterator.hasNext()) {
            String[] headings = iterator.next();
            while (iterator.hasNext()) {
                String[] csvRecord = iterator.next();

                
                String schedulekey = csvRecord[5];
                String schedulevalue = csvRecord[11];
                String subjectkey = csvRecord[2];
                String subjectvaule = csvRecord[1];
                
                String crn = csvRecord[0];
                String subjectid = csvRecord[2].substring(0, 3).strip();
                String num = csvRecord[2].substring(3).strip();
                String description = csvRecord[3];
                String sectionid = csvRecord[4];
                String type = csvRecord[5];
                String credits = csvRecord[6];
                String start = csvRecord[7];
                String end = csvRecord[8];
                String days = csvRecord[9];
                String where = csvRecord[10];
                String[] instructorsvalues = csvRecord[12].split(",");
                
                
                for (int i = 0; i < headings.length; ++i) {

                    coursevalues.put(SUBJECTID_COL_HEADER, subjectkey);
                    coursevalues.put(NUM_COL_HEADER, num);
                    coursevalues.put(DESCRIPTION_COL_HEADER, description);
                    coursevalues.put(CREDITS_COL_HEADER, credits);
                    
                    course.put(subjectid, coursevalues);
                    
                    
                    sectionvalues.put(CRN_COL_HEADER, crn);
                    sectionvalues.put(SUBJECTID_COL_HEADER, subjectid);
                    sectionvalues.put(NUM_COL_HEADER, num);
                    sectionvalues.put(SECTION_COL_HEADER, sectionid);
                    sectionvalues.put(TYPE_COL_HEADER, type);
                    sectionvalues.put(START_COL_HEADER, start);
                    sectionvalues.put(END_COL_HEADER, end);
                    sectionvalues.put(DAYS_COL_HEADER, days);
                    sectionvalues.put(WHERE_COL_HEADER, where);
                    
                    sectionarray.add(sectionvalues);
                    instructors.put(INSTRUCTOR_COL_HEADER,instructorsvalues);
                    //System.out.println(instructorsvalues);
                    sectionarray.add(instructors);
                    
                    
                    
                    
                    
                    if (schedule.containsKey(schedulekey) == false){
                        schedule.put(schedulekey, schedulevalue);
                    }
        

                    
                    if (subject.containsKey(subjectkey) == false){
                        subject.put(subjectkey, subjectvaule);
                    }   
                    
                    
                    
                    
                    
                    
                    
                }

                
                    
                
                
                
 
            }

        schedulemap.put("scheduletype", schedule);
        subjectmap.put("subject", subject);
        coursemap.put("course", course);
        sectionmap.put("section", sectionarray);
        
        
        schedulemap.toString();

        subjectmap.toString();
     
        coursemap.toString();
      
        sectionmap.toString();
        
        records.add(schedulemap);
        records.add(subjectmap);
        records.add(coursemap);
        records.add(sectionmap);
        
        
        
        
        
        

        }
    
        //System.out.println(records.toJson());

        
        return records.toString(); // remove this!
        
    }
    
    public String convertJsonToCsvString(JsonObject json) {
           
        String jsonString = json.toJson();

        JsonObject jsonObject = Jsoner.deserialize(jsonString, new JsonObject());

        String scheduletype = jsonObject.get("scheduletype").toString();
        String sectionmap = jsonObject.get("section").toString();
        
        JsonObject sectionObject = Jsoner.deserialize(sectionmap, new JsonObject());
        
        
        //System.out.println(scheduletype);
        //System.out.println(jsonString);
        
        

        

     
        
        
        
        List<String> headers = new ArrayList<>();
        
        headers.add(CRN_COL_HEADER);
        headers.add(SUBJECT_COL_HEADER);
        headers.add(NUM_COL_HEADER);
        headers.add(DESCRIPTION_COL_HEADER);
        headers.add(SECTION_COL_HEADER);
        headers.add(TYPE_COL_HEADER);
        headers.add(CREDITS_COL_HEADER); 
        headers.add(START_COL_HEADER);
        headers.add(END_COL_HEADER); 
        headers.add(DAYS_COL_HEADER); 
        headers.add(WHERE_COL_HEADER); 
        headers.add(SCHEDULE_COL_HEADER); 
        headers.add(INSTRUCTOR_COL_HEADER); 
  
        
        StringWriter writer = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(writer, '\t', '"', '\\', "\n");        
        

        csvWriter.writeNext(headers.toArray(new String[0]));
        
        List<Object> body = new ArrayList<>();
        
        for (int i=0; i<sectionmap.length(); i++){
            
            String crn = sectionObject.get("crn").toString();
            body.add(crn);
            
            String subjectid = sectionObject.get("subjectid").toString();
            body.add(subjectid);
            
            String stringnum = sectionObject.get("num").toString();
            int    num = Integer.parseInt(stringnum);
            body.add(num);
            
            String section = sectionObject.get("section").toString();
            body.add(section);
            
            String type = sectionObject.get("type").toString();
            body.add(type);
            
            String start = sectionObject.get("start").toString();
            body.add(start);
            
            String end = sectionObject.get("end").toString();
            body.add(end);
            
            String days = sectionObject.get("days").toString();
            body.add(days);
            
            String where = sectionObject.get("where").toString();
            body.add(where);
            
            String instructor = sectionObject.get("instructor").toString();
            body.add(instructor);
            
            csvWriter.writeNext(body.toArray(new String[0]));
            
        }
      
        
        
        
        String csvString = writer.toString();
        



       

            
        
     
        
        
        
        
        return csvString;
        
    }
    
    public JsonObject getJson() {
        
        JsonObject json = getJson(getInputFileData(JSON_FILENAME));
        
        return json;
        
    }
    
    public JsonObject getJson(String input) {
        
        JsonObject json = null;
        
        try {
            json = (JsonObject)Jsoner.deserialize(input);
        }
        catch (Exception e) { e.printStackTrace(); }
        
        return json;
        
    }
    
    public List<String[]> getCsv() {
        
        List<String[]> csv = getCsv(getInputFileData(CSV_FILENAME));
        return csv;
        
    }
    
    public List<String[]> getCsv(String input) {
        
        List<String[]> csv = null;
        
        try {
            
            CSVReader reader = new CSVReaderBuilder(new StringReader(input)).withCSVParser(new CSVParserBuilder().withSeparator('\t').build()).build();
            csv = reader.readAll();
            
        }
        catch (Exception e) { e.printStackTrace(); }
        
        return csv;
        
    }
    
    public String getCsvString(List<String[]> csv) {
        
        StringWriter writer = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(writer, '\t', '"', '\\', "\n");
        
        csvWriter.writeAll(csv);
        
        return writer.toString();
        
    }
    
    private String getInputFileData(String filename) {
        
        StringBuilder buffer = new StringBuilder();
        String line;
        
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        
        try {
        
            BufferedReader reader = new BufferedReader(new InputStreamReader(loader.getResourceAsStream("resources" + File.separator + filename)));

            while((line = reader.readLine()) != null) {
                buffer.append(line).append('\n');
            }
            
        }
        catch (Exception e) { e.printStackTrace(); }
        
        return buffer.toString();
        
    }
    
}