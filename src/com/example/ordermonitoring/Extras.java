package com.example.ordermonitoring;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Extras extends Fragment implements OnClickListener {
	
	Button backup, importDb;
	public static String PACKAGE_NAME;

	public Extras() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		PACKAGE_NAME = getActivity().getApplicationContext().getPackageName();
		View rootView = inflater.inflate(R.layout.tictactoe, container, false);
		backup = (Button) rootView.findViewById(R.id.bBackup);
		importDb = (Button) rootView.findViewById(R.id.bImport);
		backup.setOnClickListener(this);
		importDb.setOnClickListener(this);
		return rootView;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.bBackup:
			try {
                File sd = Environment.getExternalStorageDirectory();
                File data = Environment.getDataDirectory();

                if (sd.canWrite()) {
                	//CREATE MRBINATOG FOLDER INSIDE DATA FOLDER OF SDCARD0
                	File MrBDir = new File(Environment.getExternalStorageDirectory() + "/data/MrBinatog");
                	MrBDir.mkdirs();
                	
                    String  currentDBPath= "//data//" + PACKAGE_NAME
                            + "//databases//OrderRecord";
                    String backupDBPath  = "/data/MrBinatog/OrderRecord.db";
                    File currentDB = new File(data, currentDBPath);
                    File backupDB = new File(sd, backupDBPath);

                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    Toast.makeText(getActivity(), "Successfully saved data to " + backupDB.toString(),
                            Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {
            	e.printStackTrace();
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG)
                        .show();
            }
			break;
		case R.id.bImport:
			AlertDialog warning = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT).create();
			warning.setTitle("Warning");
			warning.setMessage("Importing Data will replace your entire records from the beginning. Make sure you are importing the correct data");
			warning.setButton(warning.BUTTON_POSITIVE, "Of course I know what I'm doing!", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					File sd = Environment.getExternalStorageDirectory();
					File appData = Environment.getDataDirectory();
					
					if (sd.canRead()) {
						String sourceDbPath = "/data/MrBinatog/OrderRecord.db";
						String dbDestination = "//data//" + PACKAGE_NAME + "//databases//OrderRecord";
						File srcDbFile = new File(sd, sourceDbPath);
						File destFile = new File(appData, dbDestination);
						try {
							FileChannel src = new FileInputStream(srcDbFile).getChannel();
							FileChannel dst = new FileOutputStream(destFile).getChannel();
							dst.transferFrom(src, 0, src.size());
							src.close();
							dst.close();
							Toast.makeText(getActivity(), "Data Successfully Imported!", Toast.LENGTH_SHORT).show();
						} catch (FileNotFoundException e) {
							Toast.makeText(getActivity(), "Nothing to import", Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						} catch (IOException e) {
							Toast.makeText(getActivity(), "An error has occured", Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						}
					}
					else {
						Toast.makeText(getActivity(), "Cannot access sd card", Toast.LENGTH_SHORT).show();
					}
				}
			});
			warning.show();
			break;
		}
	}
}