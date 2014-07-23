package org.cyk.system.kwordz.business.impl.lyrics.parser;

/*
public class LyricsParser extends AbstractParser<Lyrics> implements Serializable{

	private static final long serialVersionUID = 1L;
	private ComponentParser componentParser = new ComponentParser();
	private ChordParser chordParser = new ChordParser();
	private ChordFormatter chordFormatter = new ChordFormatter();
	
	private class EnglishParser extends AbstractLocaleParser<Lyrics>{

		private static final long serialVersionUID = 1L;

		public EnglishParser() {
			super(Locale.ENGLISH);
		}

		@Override
		public Lyrics parse(String text) throws Exception {
			text = clean(text);
			BufferedReader bufferedReader = new BufferedReader(new StringReader(text));
			List<String> lines = new LinkedList<String>();
			try {
				for(String line = bufferedReader.readLine();line!=null;line = bufferedReader.readLine())
					lines.add(line);
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE,e.toString(), e);
				return null;
			}
			String line;
			StringBuilder result = new StringBuilder();
			int chordLine=0;
			int numberOfLines = lines.size();
			for(chordLine=0;chordLine<numberOfLines-1;){
				if(processIfChordLine(lines.get(chordLine), result,chordLine,numberOfLines))
					chordLine++;
				else{
					line = mergeLineFromEnglish(lines.get(chordLine), lines.get(chordLine+1));
					if(line==null){//error parsing chord -> just take the supposed chord line as a simple text
						append(result, lines.get(chordLine), chordLine, numberOfLines);
						chordLine++;
					}else{
						append(result, line, chordLine, numberOfLines);
						chordLine += 2;//we merged two lines into two
					}
				}
			}
			if(chordLine==numberOfLines-1)
				if(!processIfChordLine(lines.get(chordLine), result,chordLine,numberOfLines))
					append(result, lines.get(chordLine), chordLine, numberOfLines);
			//we got a standard form
			return LyricsParser.this.parse(result.toString().trim());
		}
		
		private void append(StringBuilder s,String line,int i,int l){
			s.append(line);
			if(i<l-1)
				s.append("\r\n");
		}
		
		private boolean processIfChordLine(String line,StringBuilder result,int k,int l){
			Object[] data = extractTagValue(line,LINE);
			if(data!=null){
				int j = ((String)data[0]).length(),i=startTag(LINE).length();
				data[0] = chordLineFromEnglish((String)data[0]);//standard extracted line
				line = line.substring(0,i) + data[0] + line.substring(i+(Integer)data[1]+j);
				append(result, line, k, l);
				return true;
			}
			return false;
		}
		
		private String chordLineFromEnglish(String chordLine){
			if(chordLine==null ||  (chordLine=chordLine.replaceAll("\\s+$", "")).isEmpty())
				return null;
			int i=0;String englishChord="",result="",builtChord;
			
			while(!chordLine.isEmpty()){
				builtChord = "";
				if(Character.isLetter(chordLine.charAt(0))){
					read chord
					englishChord = "";
					i=0;
					while(i<chordLine.length() && chordLine.charAt(i)!=' ')
						englishChord += chordLine.charAt(i++);
				}else if(!CHORD_SEPARATORS.contains(chordLine.charAt(0)))
					return null;
				if(!englishChord.isEmpty()){
					is it a valid chord
					try {
						Chord c = chordParser.parse(englishChord, Locale.ENGLISH);
						builtChord = chordFormatter.contract(c);
					} catch (Exception e) {
						return null;
					}
				}
				
				result += CHORD_START+builtChord+CHORD_END;
				chordLine = chordLine.substring(i).trim();
			}
			return result+chordLine;
		}
		
		private String mergeLineFromEnglish(String chordLine,String lyricsLine){
			if(chordLine==null ||  (chordLine=chordLine.replaceAll("\\s+$", "")).isEmpty())
				return null;
			int i=0;String englishChord="",lyric,result="",builtChord;
			
			while(!chordLine.isEmpty()){
				builtChord = "";
				if(Character.isLetter(chordLine.charAt(0))){
					read chord
					englishChord = "";
					lyric = null;
					i=0;
					while(i<chordLine.length() && chordLine.charAt(i)!=' ')
						englishChord += chordLine.charAt(i++);
				}else if(!CHORD_SEPARATORS.contains(chordLine.charAt(0)))
					return null;
				if(!englishChord.isEmpty()){
					is it a valid chord
					try {
						Chord c = chordParser.parse(englishChord, Locale.ENGLISH);
						builtChord = chordFormatter.contract(c);
					} catch (Exception e) {
						//LOGGER.log(Level.SEVERE,e.toString(),e);
						return null;
					}
				}
				//int t = i;
				if(lyricsLine==null){
					result += CHORD_START+builtChord+CHORD_END;
					chordLine = chordLine.substring(i).trim();
					continue;
				}
				
				read lyric
				while(i<chordLine.length() && CHORD_SEPARATORS.contains(chordLine.charAt(i)))
					i++;
				chordLine = chordLine.substring(i);
				if(chordLine.isEmpty())
					lyric = lyricsLine;
				else if(i<lyricsLine.length()){
					lyric = lyricsLine.substring(0,i);
					lyricsLine = lyricsLine.substring(i);
				}else{
					lyric = lyricsLine;
					lyricsLine = null;
				}
				if(englishChord.isEmpty())
					result += lyric;
				else
					result += CHORD_START+builtChord+CHORD_END+lyric;
			}
			return result;
		}

		public String clean(String text){
			if(text==null || text.isEmpty())
				return text;
			text = text.replaceAll("\\"+CHORD_START, CHORD_START_REPLACEMENT);
			text = text.replaceAll("\\"+CHORD_END,CHORD_END_REPLACEMENT);
			BufferedReader bufferedReader = new BufferedReader(new StringReader(text));
			try {
				StringBuilder s = new  StringBuilder();
				boolean emptyLineFound = false;
				for(String line=bufferedReader.readLine();line!=null;line=bufferedReader.readLine()){
					line = line.replaceAll("\\s+$", "");
					if(line.isEmpty())
						if(emptyLineFound)
							continue;
						else{
							emptyLineFound = true;
							s.append("\r\n");
						}
					else{
						emptyLineFound = false;
						s.append(line+"\r\n");
					}
				}
				text = s.toString();
			} catch (IOException e) {}
			return text;
		}
		
	}
	
	public LyricsParser() {
		chordFormatter.setShowTypeSeparator(true);
		chordFormatter.getNoteFormatter().setShowAlterationSeparator(true);
		addLocaleParser(new EnglishParser());
	}
	
	@Override
	public Lyrics parse(String text) throws Exception {
		text = text.trim();
		Lyrics lyrics = new Lyrics();
		//build components
		do{
			Object[] result = extractTagValue(text, COMPONENT);
			if(result==null)
				break;
			lyrics.getComponents().add(componentParser.parse((String) result[0]));
			text = (String) result[2];
		}while(true);
		
		return lyrics;
	}
	
}
*/
