package web_crawler_try;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class no_jsoup {

	//Queue for BFS
    static Queue<String> q = new LinkedList<>();
    
    //URLs already visited
    static Set<String> marked = new HashSet<>();
    
   //URL Pattern regex
   static String regex = "http[s]*://(\\w+\\.)*(\\w+)";
   
   //Start from here
   static String root = "https://www.youtube.com/";
    
   //BFS Routine
    public static void bfs() throws IOException{
        q.add(root);
        while(!q.isEmpty()){ 
            String s = q.poll();
            
            //Find only almost 100 websites.
            if(marked.size()>100)return;
            
            boolean ok = false;
            URL url = null;
            BufferedReader br = null;
            
            while(!ok){ 
                try{
                    url = new URL(s);
                    br = new BufferedReader(new InputStreamReader(url.openStream()));
                    ok = true;
                }catch(MalformedURLException e){
                    System.out.println("\nMalformedURL : "+s+"\n");
                    //Get next URL from queue
                    s = q.poll();
                    ok = false;
                }catch(IOException e){
                    System.out.println("\nIOException for URL : "+s+"\n");
                    //Get next URL from queue
                    s = q.poll();
                    ok = false;
                }
            }         
            
            StringBuilder sb = new StringBuilder();
            
            while((s = br.readLine())!=null){
                sb.append(s);
            }
            s = sb.toString();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(s);
            
            while(matcher.find()){
                String w = matcher.group(); 
                
                if(!marked.contains(w)){
                    marked.add(w);
                    System.out.println("Site : "+w);
                    q.add(w);
                }
            } 
        }
    }
    
    //Display results from SET marked
    public static void displayResults(){
        System.out.println("\n\nResults: ");
        System.out.println("\nWeb sites crawled : "+marked.size()+"\n");
        for(String s:marked){
            System.out.println(s);
        }
    }
    
    //Run
    public static void main(String[] args){
        try{
            bfs();
            displayResults(); 
        }catch(IOException e){
            System.out.println("IOException caught : "+e);
        }
    }
}

	
		
		/*
		 String pattern = "https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";

    	Pattern compiledPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
    	Matcher matcher = compiledPattern.matcher("https://www.youtube.com");
    	while(matcher.find()) {
    	    System.out.println(matcher.group());
    	}
		 */
	
