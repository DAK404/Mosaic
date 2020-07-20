package API.Editor;

import API.Editor.*;

public class EditorInterface
{
	public EditorInterface()
	{
	}
	
	public void EditorScript(String Mode, String User)throws Exception
	{
		API.policyEnforce pe=new API.policyEnforce("Editor");
		
		if(pe.checkPolicy()==false)
		{
			return;
		}
		
		if(Mode.equals("Read"))
		{
			ReadFile r=new ReadFile(User);
			r.ShowFile();
		}
		else if (Mode.equals("Write"))
		{
			textEdit te=new textEdit(User);
			te.editScript();
		}
	}
}