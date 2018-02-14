package org.rbccps.controller;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.QueryParam;

import java.util.*;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.FileNotFoundException;
import java.io.FileReader;


@Path("/videostream")
@Produces(MediaType.APPLICATION_JSON)
public class videoBackend {

	static String csv= "/home/vasanth/realserver-backup-list.csv";
	BufferedReader br = null;
	String line = "";
    String cvsSplitBy = ",";
    
        @POST
        public List<String> createStream(@Context HttpHeaders headers, @QueryParam("id") String id, @QueryParam("playurl") String playurl) {
               List<String> responses=new ArrayList<String>();
                try {

                    br = new BufferedReader(new FileReader(csv));
                    br.readLine(); // consume first line and ignore
                    while ((line = br.readLine()) != null) {

                        // use comma as separator
                        String[] RIP = line.split(cvsSplitBy);

                        System.out.println(RIP[0]);
                        String response;
                        String pwd = headers.getRequestHeader("pwd").get(0);
                        String nocheck = headers.getRequestHeader("no-check").get(0);
                        System.out.println(pwd);
                        System.out.println(nocheck);
                        System.out.println(id);
                        System.out.println(playurl);
                        try {

                            URL url = new URL("http://"+RIP[0]+":8088/create_stream?id="+id+"&playurl="+playurl);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            conn.setRequestProperty("Content-Type",
                            		"application/x-www-form-urlencoded");
                            conn.setRequestProperty("no-check", nocheck);
                            conn.setRequestProperty("pwd", pwd);
                            conn.setDoOutput(true);
                            Reader in = new BufferedReader(new InputStreamReader(
                                            conn.getInputStream(), "UTF-8"));
                            StringBuilder sb = new StringBuilder();
                            for (int c; (c = in.read()) >= 0;)
                                    sb.append((char) c);
                            response = sb.toString();
                            System.out.println(response);
                            responses.add(RIP[0]+"'s response: "+response);
                        } 
                        catch (IOException e) {
                            // TODO Auto-generated catch block
                            // e.printStackTrace();'s response
                            response = "Could not add stream";
                            System.out.println(response);
                            responses.add(RIP[0]+"'s response: "+response);
                    }
                    }
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
               
               return responses;
    }
        
        @DELETE
        public List<String> removeStream(@Context HttpHeaders headers, @QueryParam("id") String id) {
               List<String> responses=new ArrayList<String>();
                try {

                    br = new BufferedReader(new FileReader(csv));
                    br.readLine(); // consume first line and ignore
                    while ((line = br.readLine()) != null) {

                        // use comma as separator
                        String[] RIP = line.split(cvsSplitBy);

                        System.out.println(RIP[0]);
                        String response;
                        String pwd = headers.getRequestHeader("pwd").get(0);
                        String nocheck = headers.getRequestHeader("no-check").get(0);
                        System.out.println(pwd);
                        System.out.println(nocheck);
                        System.out.println(id);
                        try {

                            URL url = new URL("http://"+RIP[0]+":8088/remove_stream?id="+id);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            conn.setRequestProperty("Content-Type",
                            		"application/x-www-form-urlencoded");
                            conn.setRequestProperty("no-check", nocheck);
                            conn.setRequestProperty("pwd", pwd);
                            conn.setDoOutput(true);
                            Reader in = new BufferedReader(new InputStreamReader(
                                            conn.getInputStream(), "UTF-8"));
                            StringBuilder sb = new StringBuilder();
                            for (int c; (c = in.read()) >= 0;)
                                    sb.append((char) c);
                            response = sb.toString();
                            System.out.println(response);
                            responses.add(RIP[0]+"'s response: "+response);
                        } 
                        catch (IOException e) {
                            // TODO Auto-generated catch block
                            // e.printStackTrace();
                            response = "Could not remove stream";
                            System.out.println(response);
                            responses.add(RIP[0]+"'s response: "+response);
                    }
                    }
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
               
               return responses;
    }

}

                
