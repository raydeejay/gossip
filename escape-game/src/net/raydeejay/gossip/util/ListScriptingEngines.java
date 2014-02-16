package net.raydeejay.gossip.util;

import java.util.List;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

public class ListScriptingEngines {
  
  public static void main(String [] args) {
    ScriptEngineManager sem = new ScriptEngineManager();
    List<ScriptEngineFactory> list = sem.getEngineFactories();

    for (int i = 0; i < list.size(); i++) {
      ScriptEngineFactory engineFactory = list.get(i);
      String engineName = engineFactory.getEngineName();
      String engineVersion = engineFactory.getEngineVersion();
      String langName = engineFactory.getLanguageName();
      String langVersion = engineFactory.getLanguageVersion();
	  
      System.out.println(engineName + " " +
        engineVersion + " " +
        langName + " " +
        langVersion);
/*      String statement = engineFactory.getOutputStatement("\"hello, world\"");
      System.out.println(statement);

      ScriptEngine e = engineFactory.getScriptEngine();
      try {
        e.eval(statement);
      } catch (ScriptException ex) {
        ex.printStackTrace();
      }
*/	  
    }
  }
}