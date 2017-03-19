import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Stack;

public class Timer {
	
	private static Timer instance = new Timer();
	
	private Boolean isActive;
	private String buffer;
	private Stack<String> runningTimers;
	private Stack<Long> startTimes;
	
	private Timer() {
		isActive = false;
		buffer = "";
		runningTimers = new Stack<String>();
		startTimes = new Stack<Long>();
	}
	
	public static Timer getInstance() {
		return instance;
	}

	public void startTiming(String comment) {
		if(!isActive) {
			return;
		}
		
		String indent = getIndent();
		
		buffer += indent + "STARTTIMING: " + comment + "\n";
		runningTimers.add(comment);
		startTimes.add(System.currentTimeMillis());
	}
	
	public void stopTiming(String comment) {
		if(!isActive) {
			return;
		}
		
		long elapsedTime;
		String indent;
		
		if(runningTimers.peek() != comment) {
			throw new Error("Cannot remove '" + comment + "' as it is not the most recent timer or does not exist.");
		} else {
			runningTimers.pop();
			indent = getIndent();
			elapsedTime = System.currentTimeMillis() - startTimes.pop();
			buffer += indent + "STOPTIMING: " + comment + " " + elapsedTime + "ms" + "\n";
		}
		
		if(runningTimers.size() == 0) {
			buffer += "TOTAL TIME: " + elapsedTime + "ms\n";
		}
	}
	
	public void comment(String comment) {
		if(!isActive) {
			return;
		}
		
		String indent = getIndent(-1);
		
		buffer += indent + "COMMENT: " + comment + "\n";
	}
	
	private String getIndent() {
		String indent = "";
		
		for(int i = 0; i < runningTimers.size(); i ++) {
			indent += "| ";
		}
		
		return indent;
		
	}
	
	private String getIndent(int offset) {
		String indent = "";
		
		for(int i = 0; i < runningTimers.size() + offset; i ++) {
			indent += "| ";
		}
		
		return indent;
	}
	
	public void dump(String filename) {
		if(!isActive) {
			return;
		}
		
		Path path;
		
		if(filename == null) {
			path = Paths.get(generateTimestamp());
		} else {
			path = Paths.get(filename);
		} 
		
		List<String> lines = Arrays.asList(buffer.split("\n"));
		
		try {
			Files.write(path, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String generateTimestamp() {
		
		Date date = new Date();

		SimpleDateFormat ft = new SimpleDateFormat("ddyyMMhhmmss");
		
		String timestamp = ft.format(date);

		return "instrumentation" + timestamp + ".log";
	}
	
	public void activate(Boolean onOff) {
		this.isActive = onOff;
	}
	
}
