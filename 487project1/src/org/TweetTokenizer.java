package org;
import java.util.Enumeration;

public class TweetTokenizer implements Enumeration<Object>{
		  String _value;
		  
		  public TweetTokenizer(String value){
			  _value=value;
		  }
	  	  

		  //determines if there is another opening <tt> tag i.e. another tweet text in the file
		@Override
		public boolean hasMoreElements() {
			for(int i=0; i<_value.length()+4;i++){
				if(_value.charAt(i)=='<'&&_value.charAt(i+1)=='t'&&_value.charAt(i+2)=='t'
						&&_value.charAt(i+3)=='>'){
					//found <tt> tag
								return true;
				}
			}
			return false;
		}

		//gets the next tweet i.e. everything between <tt> </tt> tags
		@Override
		public String nextElement() {
			String s="";
			for(int i=0; i<_value.length()+4;i++){
				if(_value.charAt(i)=='<'&&_value.charAt(i+1)=='t'&&_value.charAt(i+2)=='t'
						&&_value.charAt(i+3)=='>'){
					//found opening tag <tt>
							for(int j=i+4;j<_value.length()+5;j++){
								if(_value.charAt(j)=='<'&&_value.charAt(j+1)=='/'&&
										_value.charAt(j+2)=='t'&&_value.charAt(j+3)=='t'&&
										_value.charAt(j+4)=='>'){
									//found closing tag </tt>
									return s;
								}else{
									s=s+_value.charAt(j);
								}
							}
				}
			}
			return "";
		}
 
	  }