package org.cyk.system.kwordz.model.music;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.cyk.system.root.model.RootModelLayer;

public class ChordStructure implements Serializable{
	
	private static Map<String,ChordStructure> MAP;
	private static final Logger LOGGER = Logger.getLogger(ChordStructure.class.getName());
	private static final String NAME = ".label";
	private static final String DESCRIPTION = ".description";	
	private static final String SYMBOL = ".symbols";
		
	private static final long serialVersionUID = 1L;
	
	/* refer to music properties file entry */
	private String id;
	/* filled with data from file */
	private List<Float> intervals = new LinkedList<Float>();
	
	public static void init() throws IOException{
		MAP = new HashMap<String, ChordStructure>();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
				ChordStructure.class.getResourceAsStream("chord_structures.txt")));
		List<String> entries = new LinkedList<String>();
		for(String line = bufferedReader.readLine();line!=null;line = bufferedReader.readLine())
			entries.add(line);
		MAP.clear();
		for(String entry : entries){
			String[] p = entry.split("#");
			List<Float> intervals = new ArrayList<Float>();
			for(String s : p[1].split(" "))
				intervals.add(Float.parseFloat(s));
			MAP.put(p[0],new ChordStructure(p[0],intervals));
		}
	}
	
	public static Map<String, ChordStructure> getList() {
		if(MAP==null)
			try {
				init();
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE,e.toString(),e);
			}
		return MAP;
	}
	
	public static ChordStructure getById(String id){
		return getList().get(id);
	}
	
	public static Collection<ChordStructure> getAll(){
		return getList().values();
	}
	
	public static ChordStructure getBySymbol(String symbol){
		for(Entry<String, ChordStructure> entry : getList().entrySet())
			if(entry.getValue().getSymbols().contains(symbol))
				return entry.getValue();
		return null;
	}
	
	public List<Note> generateSequence(Note base){
		List<Note> notes = new ArrayList<Note>();
		Note next = new Note(base);
		notes.add(new Note(next));
		for(float interv : intervals){
			//next.transpose(interv);
			notes.add(new Note(next));
		}
		return notes;
	}
	
	public ChordStructure() {}
	
	private ChordStructure(String id,List<Float> intervals) {
		this.id = id;
		this.intervals = intervals;
	}
	public String getId() {
		return id;
	}
	public String getSymbol() {
		return  getSymbols().get(0);
	}
	
	public List<String> getSymbols() {
		return Arrays.asList(RootModelLayer.getInstance().i18n(id+SYMBOL).split(","));
	}
	public List<Float> getIntervals() {
		return intervals;
	}
	
	public void setIntervals(List<Float> intervals) {
		this.intervals = intervals;
	}
	public String getName() {
		return RootModelLayer.getInstance().i18n(id+NAME);
	}

	public String getDescription() {
		return RootModelLayer.getInstance().i18n(id+DESCRIPTION);
	}
	public String symbolsAsString(){
		String result = "";
		for(String string : getSymbols())
			result += string+",";
		result = result.substring(0, result.length()-1);
		return result;
	}
	
	@Override
	public String toString() {
		return id;
	}
	
	public static void main(String args[]){
		try {
			ChordStructure.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
