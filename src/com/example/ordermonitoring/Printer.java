package com.example.ordermonitoring;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class Printer {
	
	private File sdcard = Environment.getExternalStorageDirectory();
	private File path;
	private File generatedFile;
	private FileWriter writer;
	
	public void generatePrintableDoc(String filename, String stringToWrite, Context context) {
		if (sdcard.canWrite()) {
			path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
			generatedFile = new File(path, filename + ".doc");
			try {
				generatedFile.createNewFile();
				writer = new FileWriter(generatedFile, true);
				writer.append(stringToWrite + "\n");
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
				Toast.makeText(context, "Failed to save in " + generatedFile.toString(), Toast.LENGTH_SHORT).show();
			}

		} else {
			Toast.makeText(context, "Cannot access sdcard", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void deleteExistingFile(String filename) throws FileNotFoundException {
		if (sdcard.canWrite()) {
			path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
			generatedFile = new File(path, filename + ".doc");
			generatedFile.delete();
		}
	}

}
