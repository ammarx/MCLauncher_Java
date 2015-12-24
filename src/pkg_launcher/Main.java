/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg_launcher;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import sk.tomsik68.mclauncher.api.common.ILaunchSettings;
import sk.tomsik68.mclauncher.api.common.IObservable;
import sk.tomsik68.mclauncher.api.common.IObserver;
import sk.tomsik68.mclauncher.api.common.mc.MinecraftInstance;
import sk.tomsik68.mclauncher.api.login.IProfile;
import sk.tomsik68.mclauncher.api.login.ISession;
import sk.tomsik68.mclauncher.api.versions.IVersion;
import sk.tomsik68.mclauncher.impl.common.Platform;
import sk.tomsik68.mclauncher.impl.login.yggdrasil.YDLoginService;
import sk.tomsik68.mclauncher.impl.login.yggdrasil.YDProfileIO;
import sk.tomsik68.mclauncher.impl.versions.mcdownload.MCDownloadVersionList;

import sk.tomsik68.mclauncher.api.common.IObservable;
import sk.tomsik68.mclauncher.api.common.IObserver;



import sk.tomsik68.mclauncher.api.common.IObservable;
import sk.tomsik68.mclauncher.api.common.IObserver;
import sk.tomsik68.mclauncher.api.common.mc.MinecraftInstance;
import sk.tomsik68.mclauncher.api.versions.IVersion;
import sk.tomsik68.mclauncher.impl.versions.mcdownload.MCDownloadVersionList;


import sk.tomsik68.mclauncher.api.common.IObservable;
import sk.tomsik68.mclauncher.api.common.IObserver;
import sk.tomsik68.mclauncher.api.common.IOperatingSystem;
import sk.tomsik68.mclauncher.api.common.MCLauncherAPI;
import sk.tomsik68.mclauncher.api.common.mc.MinecraftInstance;
import sk.tomsik68.mclauncher.api.versions.IVersion;
import sk.tomsik68.mclauncher.impl.versions.mcassets.MCAssetsVersionList;

/**
 *
 * @author ammar
 */
public class Main extends javax.swing.JFrame {

    
    
        public void savesettings(){
            Properties prop = new Properties();
	OutputStream output = null;

	try {

		output = new FileOutputStream("config.properties");

		// set the properties value
		prop.setProperty("username", jTextField1.getText());
		prop.setProperty("selectedversion", (String) jComboBox1.getSelectedItem());
		
		// save properties to project root folder
		prop.store(output, null);

	} catch (IOException io) {
		io.printStackTrace();
	} finally {
		if (output != null) {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
        }
    
        
        public void loadsettings(){
            
            Properties prop = new Properties();
	InputStream input = null;

	try {

		input = new FileInputStream("config.properties");

		// load a properties file
		prop.load(input);

		// get the property value and print it out
		//System.out.println(prop.getProperty("database"));
		//System.out.println(prop.getProperty("dbuser"));
		//System.out.println(prop.getProperty("dbpassword"));

                jTextField1.setText(prop.getProperty("username"));
		jComboBox1.setSelectedItem(prop.getProperty("selectedversion"));
		
	} catch (IOException ex) {
		ex.printStackTrace();
	} finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
        }
    
       public void test() {

	try {
	    // finally use my minecraft credentials
	    System.out.println("Logging in...");
	   
            
            YDLoginService service = new YDLoginService();
            service.load(Platform.getCurrentPlatform().getWorkingDirectory());
            YDProfileIO profileIO = new YDProfileIO(Platform
                .getCurrentPlatform().getWorkingDirectory());
            
            IProfile[] profiles = profileIO.read();
            final ISession session = service.login(profiles[0]);
            
            session.setUsername(jTextField1.getText());
            session.setSessionID("NULL");
            session.setUUID(myUUID);
            
            profileIO.write(profiles);
            
	    System.out.println("Success! Launching...");
           
            
	    final MinecraftInstance mc = new MinecraftInstance(new File("testmc"));
	    final MCDownloadVersionList versionList = new MCDownloadVersionList();
	    versionList.addObserver(new IObserver<String>() {

		private boolean launched = false;
               
                
		@Override
		public void onUpdate(IObservable<String> observable,
			String id) {
                        id = (String) jComboBox1.getSelectedItem();
                        IVersion changed = null;
			try {
				changed = versionList.retrieveVersionInfo(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!launched) {
			launched = true;
			try {
			    List<String> launchCommand = changed.getLauncher()
				    .getLaunchCommand(session, mc, null,
					    changed, new ILaunchSettings() {
                                                
						@Override
						public boolean isModifyAppletOptions() {
						    return false;
						}

						@Override
						public File getJavaLocation() {
						    return null;
						}

						@Override
						public List<String> getJavaArguments() {
						    return Arrays
							    .asList("-XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_ammar.exe_javaw.exe_minecraft.exe.heapdump",
                                                                    "-XX:+UseConcMarkSweepGC",
								    "-XX:+CMSIncrementalMode",
								    "-XX:-UseAdaptiveSizePolicy",
								    "-Xmn128M");
						}

						@Override
						public String getInitHeap() {
						    return "512M";
						}

						@Override
						public String getHeap() {
						    return "1G";
						}

						@Override
						public Map<String, String> getCustomParameters() {
						    return null;
						}

						@Override
						public List<String> getCommandPrefix() {
						    return null;
						}
					    }, null);
			    for (String cmd : launchCommand) {
				System.out.print(cmd + " ");
			    }
			    System.out.println();
			    ProcessBuilder pb = new ProcessBuilder(
				    launchCommand);
			    pb.redirectError(new File("mcerr.log"));
			    pb.redirectOutput(new File("mcout.log"));
			    pb.directory(mc.getLocation());
			    Process proc = pb.start();
			    BufferedReader br = new BufferedReader(
				    new InputStreamReader(proc.getInputStream()));
			    String line;
			    while (isProcessAlive(proc)) {
                                
                                //lets just exit it here...
                                System.exit(0);
				
                                
                                line = br.readLine();
				if (line != null && line.length() > 0)
				    System.out.println(line);
			    }
			} catch (Exception e) {
			    e.printStackTrace();
			}

		    }
		}
	    });
	    versionList.startDownload();
            
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    protected boolean isProcessAlive(Process proc) {
	try {
	    System.out.println("Process exited with error code:"
		    + proc.exitValue());
            return false;
	} catch (Exception e) {
	    return true;
	}

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void test1() {
        jComboBox1.removeAllItems();
        
        MCDownloadVersionList list = new MCDownloadVersionList();
        list.addObserver(new IObserver<String>() {
            @Override
            public void onUpdate(IObservable<String> observable, String changed) {
                System.out.println(changed);
                jComboBox1.addItem(changed);
            }
        });
        try {
            list.startDownload();
        } catch (Exception e) {
            e.printStackTrace();
        }
        

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void test3() {
        final MCDownloadVersionList list = new MCDownloadVersionList();
        final MinecraftInstance mc = new MinecraftInstance(new File("testmc"));
        list.addObserver(new IObserver<String>() {
            private boolean installed = false;

            @Override
            public void onUpdate(IObservable<String> observable, String id) {
                id = (String) jComboBox1.getSelectedItem();
                
                IVersion changed = null;
                try {
                    changed = list.retrieveVersionInfo(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Version "+changed.getId());
                if(installed) return;
                installed = true;
                System.out.println("Installing " + changed.getDisplayName());
                try {
                    changed.getInstaller().install(changed, mc, null);
                } catch (Exception e) {
                    e.printStackTrace();
                    //fail();
                }
            }
        });
        try {
            list.startDownload();
        } catch (Exception e) {
            e.printStackTrace();
            //fail();

        }
    }
    
    
    
    
    
    
    
    
    
    
    
    public void test4() {
        final MinecraftInstance mc = new MinecraftInstance(new File("testmc"));
        mc.getLocation().mkdirs();
        final MCAssetsVersionList list = new MCAssetsVersionList();
        list.addObserver(new IObserver<String>() {
            private boolean installed = false;

            @Override
            public void onUpdate(IObservable<String> observable, String id) {
                id = (String) jComboBox1.getSelectedItem();
                IVersion changed = null;
                try {
                    changed = list.retrieveVersionInfo(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!installed) {
                    installed = true;
                    System.out.println("Found version: " + changed.getDisplayName() + " installing");
                    try {
                        changed.getInstaller().install(changed, mc, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                       
                    }
                }
            }
        });
        try {
            list.startDownload();
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        setResizable(false);
       
      
        
        jComboBox1.removeAllItems();
        jComboBox1.enable(false);
        jTextField1.setText("Steve");
        jTextField1.grabFocus();
        
        
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                //test1(); //version list (FULL LIST)
                //test1 disabled.. moved to new form.
                final MinecraftInstance mc = new MinecraftInstance(new File("testmc"));
                System.out.print(mc.getLocation().toString());
                jComboBox1.removeAllItems();
                listFolders(mc.getLocation().toString() + "/versions/");
                jComboBox1.enable(true);
                loadsettings();
                getSkin();
                return null;
            }
        } );
        executor.shutdown();
        //test1(); //version list (FULL LIST)
        //test();  //run minecraft
        //test3(); //download latest (IF NO VERSION GIVEN..)
        //test4(); //installing...
        
//        System.out.println(System.getProperty("user.home"));
//        jComboBox1.removeAllItems();
//        listFolders(System.getProperty("user.home") + "/.minecraft/versions/");
        
        //listFolders(directoryName);
        //on load.. get all items
        
       
    }

    public void listFolders(String directoryName) {
        File directory = new File(directoryName);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isDirectory()) {
                //System.out.println(file.getName());
                jComboBox1.addItem(file.getName());
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        jMenuItem1.setText("Cut");
        jPopupMenu1.add(jMenuItem1);

        jMenuItem2.setText("Copy");
        jPopupMenu1.add(jMenuItem2);
        jPopupMenu1.add(jSeparator1);

        jMenuItem3.setText("Paste");
        jMenuItem3.setToolTipText("");
        jPopupMenu1.add(jMenuItem3);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Launcher - 1.0");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTextField1.setText("Steve");
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTextField1MouseReleased(evt);
            }
        });
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        jLabel1.setText("Name");

        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pkg_launcher/res/Steve.png"))); // NOI18N

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton2.setText("Launch");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Version");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pkg_launcher/res/cogs3.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 68, Short.MAX_VALUE))
                            .addComponent(jTextField1)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton3)))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
       System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    
    public static String myUUID = "NULL";
    
    
    public void getSkin(){
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                String targetURL = "https://api.mojang.com/profiles/minecraft";
                String urlParameters = "[\"" + jTextField1.getText() + "\"]";
                HttpURLConnection connection = null;
                try {
                    //Create connection
                    URL url = new URL(targetURL);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type",
                            "application/json");

                    connection.setRequestProperty("Content-Length",
                            Integer.toString(urlParameters.getBytes().length));
                    connection.setRequestProperty("Content-Language", "en-US");

                    connection.setUseCaches(false);
                    connection.setDoOutput(true);

                    //Send request
                    DataOutputStream wr = new DataOutputStream(
                            connection.getOutputStream());
                    wr.writeBytes(urlParameters);
                    wr.close();

                    //Get Response  
                    InputStream is = connection.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                    StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+ 
                    String line;
                    while ((line = rd.readLine()) != null) {
                        response.append(line);
                        response.append('\r');
                    }
                    rd.close();
                    //return response.toString();

                    System.out.println(response.toString());
                    if (response.toString() == "[]"){
                        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pkg_launcher/res/Steve.png"))); // NOI18N

                    }
                    //jTextField1.setText(response.toString());
                    JsonObject jsonObject = new JsonParser().parse(response.toString().replace("[", "").replace("]", "")).getAsJsonObject();

                    //jTextField1.setText(jsonObject.get("name").getAsString());
                    myUUID = jsonObject.get("id").getAsString();
                    //JOptionPane.showMessageDialog(null, myUUID, "InfoBox: " + "Launcher 1.0", JOptionPane.INFORMATION_MESSAGE);
    
                    Image image = null;
                    try {
                        URL urlx = new URL("https://crafatar.com/renders/body/" + jTextField1.getText() + "?scale=6");
                        image = ImageIO.read(urlx);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ImageIcon icon = new ImageIcon(image);
                    jLabel2.setIcon(icon);

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }

                }
                return null;
            }

        }
        );

        executor.shutdown();
    }
    
    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jTextField1ActionPerformed

    public void launchminecraftyo(){
            
        savesettings();
        
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                //test3(); //download latest (IF NO VERSION GIVEN..)
                //test4(); //installing...
                test();  //run minecraft
                return null;
            }
        }
        );
        executor.shutdown();

        
    }
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
     launchminecraftyo();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        Download download = new Download();
        //download.setDefaultCloseOperation(download.HIDE_ON_CLOSE);
        download.setVisible(true);
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost
        // TODO add your handling code here:
        getSkin();
    }//GEN-LAST:event_jTextField1FocusLost

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
         // Enter was pressed. Your code goes here.
         //System.out.print("Hello");
         launchminecraftyo();
         //we launch minecraft here...
         
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void jTextField1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseReleased
        // TODO add your handling code here:
        /*if(evt.isPopupTrigger()){
            jPopupMenu1.show(this,evt.getX(),evt.getY());
        }*/
        
    }//GEN-LAST:event_jTextField1MouseReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        /*try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        */
        //</editor-fold>

        /* Create and display the form */
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
