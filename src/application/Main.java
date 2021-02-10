package application;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Modality;	

public class Main extends Application {
	
	Network network = new Network();
	
	public void start(Stage Stage) {
		try {
			Group root = new Group();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			GridPane gridpane = new GridPane();
			root.getChildren().add(gridpane);
			gridpane.setPrefWidth(600);
			gridpane.setPrefHeight(340);
			gridpane.setBackground(new Background(new BackgroundFill(Paint.valueOf("#F5F5F5"), null, null)));
			
			Stage.setTitle("Undirected Network Analysis Tool - By Yiliang Hu");
			Stage.getIcons().add(new Image("/icon/icon.png"));
			Stage.setWidth(600);
			Stage.setHeight(340);
			Stage.setResizable(false);
			Stage.initStyle(StageStyle.UNIFIED);
			Stage.setScene(scene);
			
			Button b1 = new Button("Browse .txt File");
			b1.setPrefWidth(130); b1.setPrefHeight(20);
			b1.setTranslateX(185);b1.setTranslateY(50);	
			
			Button newnet = new Button("Restart with a New Network");
			newnet.setDisable(true);
			newnet.setPrefWidth(180); newnet.setPrefHeight(20);
			newnet.setTranslateX(385);newnet.setTranslateY(50);	
			
			Button helpButton = new Button("Help");
			helpButton.setPrefWidth(60); helpButton.setPrefHeight(20);
			helpButton.setTranslateX(320);helpButton.setTranslateY(50);	

			Button add = new Button("Add a New Interaction");
			add.setDisable(true);
			add.setPrefWidth(230); add.setPrefHeight(20);
			add.setTranslateX(50);add.setTranslateY(150);
			
			Button hub = new Button("Find Hub(s) of Network");
			hub.setDisable(true);
			hub.setPrefWidth(230); hub.setPrefHeight(20);
			hub.setTranslateX(290);hub.setTranslateY(150);
			
			Button dd = new Button("Calculate Distribution of Degree");
			dd.setDisable(true);
			dd.setPrefWidth(230); dd.setPrefHeight(20);
			dd.setTranslateX(290);dd.setTranslateY(190);
			
			Button calcdegree = new Button("Calculate Degree of Node");
			calcdegree.setDisable(true);
			calcdegree.setPrefWidth(230); calcdegree.setPrefHeight(20);
			calcdegree.setTranslateX(50);calcdegree.setTranslateY(190);
			
			Button averdegree = new Button("Calculate Average Degree");
			averdegree.setDisable(true);
			averdegree.setPrefWidth(230); averdegree.setPrefHeight(20);
			averdegree.setTranslateX(50);averdegree.setTranslateY(230);
			
			Button detail = new Button("Details of current network");
			detail.setDisable(true);
			detail.setPrefWidth(230); detail.setPrefHeight(20);
			detail.setTranslateX(290);detail.setTranslateY(230);
			
			Button savenew = new Button("Save Current Network");
			savenew.setDisable(true);
			savenew.setPrefWidth(230); savenew.setPrefHeight(20);
			savenew.setTranslateX(170);savenew.setTranslateY(265);
			
			Text welcome = new Text(0,0,"Welcome to use Undirected Network Analysis Tool");
			welcome.setFont(Font.font(null,FontWeight.BLACK,21));
			welcome.setTranslateX(25);welcome.setTranslateY(12);	
			
			Text t1 = new Text(0,0,"Please select your file:");
			t1.setFont(Font.font(null,FontWeight.BLACK,15));
		    t1.setTranslateX(10);t1.setTranslateY(50);
			
			Text tools = new Text(0,0,"Tools");
			tools.setFont(Font.font(null,FontWeight.BOLD,15));
			tools.setTranslateX(265);tools.setTranslateY(120);

			Text t2 = new Text();
			t2.setFont(Font.font(null,FontWeight.NORMAL,13));
		    t2.setTranslateX(150);t2.setTranslateY(87);
		    
			gridpane.getChildren().addAll(newnet,b1,helpButton,add,hub,dd,calcdegree,averdegree,detail,savenew,welcome,t1,tools,t2);
			
			newnet.setOnAction(new EventHandler<ActionEvent>() { // new network button in main window
			public void handle(ActionEvent event) {
				 network = new Network();
				 String path;
				 FileChooser fileChooser = new FileChooser();
				 fileChooser.setTitle("Browse .txt File"); 
				 fileChooser.getExtensionFilters().addAll(
						 new FileChooser.ExtensionFilter("TXT", "*.txt")
				 );
				 File txtfile = fileChooser.showOpenDialog(Stage);
				 if(txtfile != null) {
					 path = txtfile.getAbsolutePath();
					 t2.setText(network.readTxt(path));
				 }
				}	
		    });
			
			b1.setOnAction(new EventHandler<ActionEvent>() { // browse button in main window
			public void handle(ActionEvent event) {	
				String path;
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Browse Txt File"); 
				fileChooser.getExtensionFilters().addAll(
						new FileChooser.ExtensionFilter("TXT", "*.txt")
						);
				File txtfile = fileChooser.showOpenDialog(Stage);
				if(txtfile != null) {
					path = txtfile.getAbsolutePath();
					t2.setText(network.readTxt(path));
					add.setDisable(false);
					calcdegree.setDisable(false);
					averdegree.setDisable(false);
					hub.setDisable(false);
					dd.setDisable(false);
					detail.setDisable(false);
					savenew.setDisable(false);
					b1.setDisable(true);
					newnet.setDisable(false);
					}
				}	
		    });
			
			
			helpButton.setOnAction(new EventHandler<ActionEvent>() {  //help button in main window
			public void handle(ActionEvent event) {
				Stage helpStage = new Stage();
				helpStage.setTitle("Help");
				helpStage.getIcons().add(new Image("/icon/icon.png"));
				helpStage.setWidth(600);
				helpStage.setHeight(490);
				helpStage.setResizable(false);
				helpStage.initStyle(StageStyle.UNIFIED);
				Group helproot = new Group();
				Scene helpscene = new Scene(helproot);
				GridPane helpgridpane = new GridPane();
				helproot.getChildren().add(helpgridpane);
				helpgridpane.setPrefWidth(600);
				helpgridpane.setPrefHeight(490);
				helpgridpane.setBackground(new Background(new BackgroundFill(Paint.valueOf("#F5F5F5"), null, null)));
				
				Text instruction = new Text(0,0,"Instructions:");
				instruction.setFont(Font.font(null,FontWeight.BLACK,14));
				instruction.setTranslateX(10);instruction.setTranslateY(30);
				
				Text t1 = new Text();
				t1.setFont(Font.font(null,FontWeight.NORMAL,14));
				t1.setTranslateX(20);t1.setTranslateY(55);		
				t1.setText("This tool is used to analyse an undirected protein-protein interaction network,\n"
						+ "'Nodes' represent proteins (use Uniprot identifiers), and 'Edges' represent inte-\n"
						+ "ractions between two Nodes. The input file must be a '.txt' file, each line repre-\n"
						+ "sents an interaction, both protein names are separated by a tabulation.");
				Text tools = new Text(0,0,"Tools:");
				tools.setFont(Font.font(null,FontWeight.BLACK,14));
				tools.setTranslateX(10);tools.setTranslateY(135);
				
				Text t2 = new Text();
				t2.setFont(Font.font(null,FontWeight.NORMAL,14));
				t2.setTranslateX(20);t2.setTranslateY(160);		
				t2.setText("1. Add Interaction: enter two node names of a new interaction and add it to cu-\n"
						+ "rrent network\n\n"
						+ "2. Calculate Degree: enter a specific node name and calculate its degree.\n\n"
						+ "3. Calculate Average Degree: Calculate the average degree of current network.\n\n"
						+ "4. Find Hub(s): Calculate the highest degree of current network and list all the\n"
						+ "nodes having that degree.\n\n"
						+ "5. Calculate Degree Distribution: Calculate degree distribution of current network \n"
						+ "and can save it into a '.txt' or '.xls' file.\n\n"
						+ "6. Save Current Network: Save current modified network to a '.txt' file.");
				
				Button helpOK = new Button("OK");
				helpOK.setPrefWidth(80); helpOK.setPrefHeight(20);
				helpOK.setTranslateX(245);helpOK.setTranslateY(415);
				helpgridpane.getChildren().add(helpOK);	
				
				helpOK.setOnAction(new EventHandler<ActionEvent>() {  //OK button in help window
					public void handle(ActionEvent event) {
						helpStage.close();
					}
				});
				
				helproot.getChildren().addAll(instruction,t1,tools,t2,helpOK);
				helpStage.setScene(helpscene);
				helpStage.show();
				}
		    });
			
			add.setOnAction(new EventHandler<ActionEvent>() {  //add interaction button in main window
				public void handle(ActionEvent event) {
				Stage addStage = new Stage();
				addStage.setTitle("Add Interaction");
				addStage.getIcons().add(new Image("/icon/icon.png"));
				addStage.setWidth(500);
				addStage.setHeight(200);
				addStage.setResizable(false);
				addStage.initStyle(StageStyle.UNIFIED);
				addStage.initModality(Modality.APPLICATION_MODAL);
				Group addroot = new Group();
				Scene addscene = new Scene(addroot);
				GridPane addgridpane = new GridPane();
				addroot.getChildren().add(addgridpane);
				addgridpane.setPrefWidth(500);
				addgridpane.setPrefHeight(200);
				addgridpane.setBackground(new Background(new BackgroundFill(Paint.valueOf("#F5F5F5"), null, null)));
				
				Text addtitle = new Text(0,0,"Please enter the name of nodes of the interaction: (e.g. P42346)");
				addtitle.setFont(Font.font(null,FontWeight.BLACK,13));
				addtitle.setTranslateX(10);addtitle.setTranslateY(10);
			    addgridpane.getChildren().add(addtitle);	
			    
				Text node1 = new Text(0,0,"Name of Node 1: ");
				node1.setFont(Font.font(null,FontWeight.NORMAL,12));
				node1.setTranslateX(130);node1.setTranslateY(40);
			    addgridpane.getChildren().add(node1);	
			    
				Text node2 = new Text(0,0,"Name of Node 2: ");
				node2.setFont(Font.font(null,FontWeight.NORMAL,12));
				node2.setTranslateX(130);node2.setTranslateY(70);
			    addgridpane.getChildren().add(node2);	
			    
				Text warning = new Text(0,0,"");
				warning.setFont(Font.font(null,FontWeight.NORMAL,12));
				warning.setTranslateX(130);warning.setTranslateY(100);
				warning.setFill(Color.RED);
			    addgridpane.getChildren().add(warning);	
				
				Button addOK = new Button("OK");
				addOK.setPrefWidth(80); addOK.setPrefHeight(20);
				addOK.setTranslateX(130);addOK.setTranslateY(130);
				addgridpane.getChildren().add(addOK);	
				
				Button addCancel = new Button("Cancel");
				addCancel.setPrefWidth(80); addCancel.setPrefHeight(20);
				addCancel.setTranslateX(270);addCancel.setTranslateY(130);
				addgridpane.getChildren().add(addCancel);	
				
				TextField node1text = new TextField();
				node1text.setPrefWidth(100);
				node1text.setTranslateX(240);node1text.setTranslateY(40);
				addroot.getChildren().add(node1text);
				
				TextField node2text = new TextField();
				node2text.setPrefWidth(100);
				node2text.setTranslateX(240);node2text.setTranslateY(70);
				addroot.getChildren().add(node2text);
				
			addCancel.setOnAction(new EventHandler<ActionEvent>() {  //cancel button in add interaction window.
					public void handle(ActionEvent event) {
						addStage.close();
						}
				    });
			addOK.setOnAction(new EventHandler<ActionEvent>() {  // ok button in add interaction window.
				public void handle(ActionEvent event) {
					// error trapping for empty or wrong format of node name
					if (node1text.getText().length()!=0 && node2text.getText().length()!=0) {
						if(node1text.getText().matches("[OPQ]\\d[A-Z0-9]{3}\\d") || node1text.getText().matches("[A-NR-Z]\\d([A-Z][A-Z0-9]{2}\\d){1,2}")) {
							if(node2text.getText().matches("[OPQ]\\d[A-Z0-9]{3}\\d") || node2text.getText().matches("[A-NR-Z]\\d([A-Z][A-Z0-9]{2}\\d){1,2}")) {
								if (!network.addInter(node1text.getText(), node2text.getText())) warning.setText("Interaction already exist!");
								else
								{t2.setText("Number of nodes in current network:\t" + String.valueOf(network.NodeList.size()) + "\nNumber of edges in current network:\t" + String.valueOf(network.EdgeList.size()));
								warning.setText("Success!");}
							}
							else warning.setText("Name of node 2 is not a Uniprot Identifier, please try again.");
						}
						else warning.setText("Name of node 1 is not a Uniprot Identifier, please try again.");
					}else warning.setText("Name cannot be empty, please try again.");
					}
			    });
			addStage.setScene(addscene);
			addStage.show();
			}
		   });
			
			calcdegree.setOnAction(new EventHandler<ActionEvent>() {  //calculate degree button in main window
			public void handle(ActionEvent event) {
				Stage calcdStage = new Stage();
				calcdStage.setTitle("Calculate Degree of Node");
				calcdStage.getIcons().add(new Image("/icon/icon.png"));
				calcdStage.setWidth(500);
				calcdStage.setHeight(170);
				calcdStage.setResizable(false);
				calcdStage.initStyle(StageStyle.UNIFIED);
				calcdStage.initModality(Modality.APPLICATION_MODAL);
				Group calcdroot = new Group();
				Scene calcdscene = new Scene(calcdroot);
				GridPane calcdgridpane = new GridPane();
				calcdroot.getChildren().add(calcdgridpane);
				calcdgridpane.setPrefWidth(500);
				calcdgridpane.setPrefHeight(170);
				calcdgridpane.setBackground(new Background(new BackgroundFill(Paint.valueOf("#F5F5F5"), null, null)));
				
				TextField nodetext = new TextField();
				nodetext.setPrefWidth(100);
				nodetext.setTranslateX(230);nodetext.setTranslateY(40);
				calcdroot.getChildren().add(nodetext);
				
				Text calcdtitle = new Text(0,0,"Please enter the name of node: (e.g. P42346)");
				calcdtitle.setFont(Font.font(null,FontWeight.BLACK,13));
				calcdtitle.setTranslateX(10);calcdtitle.setTranslateY(10);
				calcdgridpane.getChildren().add(calcdtitle);	
			    
				Text node = new Text(0,0,"Name of Node: ");
				node.setFont(Font.font(null,FontWeight.NORMAL,12));
				node.setTranslateX(130);node.setTranslateY(40);
				calcdgridpane.getChildren().add(node);
			    
				Text calcdwarning = new Text(0,0,"");
				calcdwarning.setFont(Font.font(null,FontWeight.NORMAL,12));
				calcdwarning.setTranslateX(120);calcdwarning.setTranslateY(70);
				calcdwarning.setFill(Color.RED);
				calcdgridpane.getChildren().add(calcdwarning);	
			    
				Button calcdOK = new Button("OK");
				calcdOK.setPrefWidth(80); calcdOK.setPrefHeight(20);
				calcdOK.setTranslateX(130);calcdOK.setTranslateY(100);
				calcdgridpane.getChildren().add(calcdOK);	
				
				Button calcdCancel = new Button("Cancel");
				calcdCancel.setPrefWidth(80); calcdCancel.setPrefHeight(20);
				calcdCancel.setTranslateX(270);calcdCancel.setTranslateY(100);
				calcdgridpane.getChildren().add(calcdCancel);	
				
				calcdCancel.setOnAction(new EventHandler<ActionEvent>() {  //Cancel button in calculate degree window
					public void handle(ActionEvent event) {
						calcdStage.close();
						}
				    });
				
				calcdOK.setOnAction(new EventHandler<ActionEvent>() {  //OK button in calculate degree window
					public void handle(ActionEvent event) {
						//error trapping for non-exist or empty node name.
						if(nodetext.getText().length()!=0) {
							Optional<Node> NodeOptional = network.NodeList.stream().filter(item -> item.getName().equals(nodetext.getText())).findAny();
							if(!NodeOptional.isPresent()) calcdwarning.setText("Node not found, please try again.");
							else calcdwarning.setText("The degree of " + nodetext.getText() + " is " + network.CalcDegree(nodetext.getText()));
						}else {
							calcdwarning.setText("Name cannot be empty, please try again.");
						}
					}
				});
				
				calcdStage.setScene(calcdscene);
				calcdStage.show();
				}
		    });
			
			averdegree.setOnAction(new EventHandler<ActionEvent>() {  //calculate average degree button
			public void handle(ActionEvent event) {
				Stage averStage = new Stage();
				averStage.setTitle("Calculate Average Degree");
				averStage.getIcons().add(new Image("/icon/icon.png"));
				averStage.setWidth(450);
				averStage.setHeight(130);
				averStage.setResizable(false);
				averStage.initStyle(StageStyle.UNIFIED);
				averStage.initModality(Modality.APPLICATION_MODAL);
				Group averdroot = new Group();
				Scene averdscene = new Scene(averdroot);
				GridPane calcdgridpane = new GridPane();
				averdroot.getChildren().add(calcdgridpane);
				calcdgridpane.setPrefWidth(450);
				calcdgridpane.setPrefHeight(130);
				calcdgridpane.setBackground(new Background(new BackgroundFill(Paint.valueOf("#F5F5F5"), null, null)));
				
				Text avertext = new Text(0,0,"The average degree of current network is:  " + network.CalcAverDegree());
				avertext.setFont(Font.font(null,FontWeight.BLACK,13));
				avertext.setTranslateX(50);avertext.setTranslateY(20);
				calcdgridpane.getChildren().add(avertext);	
				
				Button averdOK = new Button("OK");
				averdOK.setPrefWidth(80); averdOK.setPrefHeight(20);
				averdOK.setTranslateX(200);averdOK.setTranslateY(55);
				calcdgridpane.getChildren().add(averdOK);	
				
				averdOK.setOnAction(new EventHandler<ActionEvent>() {  //OK button in calculate average degree button
					public void handle(ActionEvent event) {
						averStage.close();
					}
				});
				
				averStage.setScene(averdscene);
				averStage.show();
				}
		    });
			
			hub.setOnAction(new EventHandler<ActionEvent>() {  //find hub button in main window
			public void handle(ActionEvent event) {
				Stage hubStage = new Stage();
				hubStage.setTitle("Find Hub(s) of Network");
				hubStage.getIcons().add(new Image("/icon/icon.png"));
				hubStage.setWidth(350);
				hubStage.setHeight(280);
				hubStage.setResizable(false);
				hubStage.initStyle(StageStyle.UNIFIED);
				hubStage.initModality(Modality.APPLICATION_MODAL);
				Group hubroot = new Group();
				Scene hubscene = new Scene(hubroot);
				GridPane hubgridpane = new GridPane();
				hubroot.getChildren().add(hubgridpane);
				hubgridpane.setPrefWidth(350);
				hubgridpane.setPrefHeight(280);
				hubgridpane.setBackground(new Background(new BackgroundFill(Paint.valueOf("#F5F5F5"), null, null)));
				
				ArrayList<String> hubs = network.FindHub();
				
				Text hubtext = new Text(0,0,"The highest degree of current network is:  " + hubs.get(0));
				hubtext.setFont(Font.font(null,FontWeight.BLACK,13));
				hubtext.setTranslateX(10);hubtext.setTranslateY(20);
				
				Text hubname = new Text(0,0,"Name of nodes have this degree:");
				hubname.setFont(Font.font(null,FontWeight.BLACK,13));
				hubname.setTranslateX(10);hubname.setTranslateY(40);
				
				TextArea hubcontent = new TextArea();
				hubcontent.setPrefWidth(150);hubcontent.setPrefHeight(120);
				hubcontent.setTranslateX(90);hubcontent.setTranslateY(60);
				String hubsname = new String();
				for (int i = 1; i < hubs.size(); i++) hubsname = hubsname + hubs.get(i) +"\n";
				hubcontent.setText(hubsname);
				
				hubroot.getChildren().addAll(hubcontent,hubtext,hubname);
				
				Button hubOK = new Button("OK");
				hubOK.setPrefWidth(80); hubOK.setPrefHeight(20);
				hubOK.setTranslateX(120);hubOK.setTranslateY(200);
				hubgridpane.getChildren().add(hubOK);	
				
				hubOK.setOnAction(new EventHandler<ActionEvent>() {  //OK button in find hub window
					public void handle(ActionEvent event) {
						hubStage.close();
					}
				});
				
				hubStage.setScene(hubscene);
				hubStage.show();
				}
		    });
			
			dd.setOnAction(new EventHandler<ActionEvent>() {  //degree distribution button in main window
			public void handle(ActionEvent event) {
				Stage ddStage = new Stage();
				ddStage.setTitle("Degree Distribution of Network"); //设置标题
				ddStage.getIcons().add(new Image("/icon/icon.png")); //设置图标
				ddStage.setWidth(300); //设置宽高
				ddStage.setHeight(390);
				ddStage.setResizable(false); //不允许改变窗口大小
				ddStage.initStyle(StageStyle.UNIFIED); //设置窗口样式
				ddStage.initModality(Modality.APPLICATION_MODAL);
				Group ddroot = new Group();
				Scene ddscene = new Scene(ddroot);
				GridPane ddgridpane = new GridPane();
				ddroot.getChildren().add(ddgridpane);
				ddgridpane.setPrefWidth(300);
				ddgridpane.setPrefHeight(390);
				ddgridpane.setBackground(new Background(new BackgroundFill(Paint.valueOf("#F5F5F5"), null, null)));
				
				Text ddtext = new Text(0,0,"The degree distribution of current \nnetwork is:  ");
				ddtext.setFont(Font.font(null,FontWeight.BLACK,13));
				ddtext.setTranslateX(10);ddtext.setTranslateY(20);
				
				Text ddtitle = new Text(0,0,"Degree \t Count");
				ddtitle.setFont(Font.font(null,FontWeight.BLACK,13));
				ddtitle.setTranslateX(85);ddtitle.setTranslateY(60);
				
				Text ddcontent = new Text();
				ddcontent.setFont(Font.font(null,FontWeight.BLACK,13));
				ddcontent.setTranslateX(95);ddcontent.setTranslateY(80);
				ddcontent.setText(network.DegDistr());
				
				Button ddOK = new Button("OK");
				ddOK.setPrefWidth(80); ddOK.setPrefHeight(20);
				ddOK.setTranslateX(50);ddOK.setTranslateY(300);	
				
				Button ddSave = new Button("Save");
				ddSave.setPrefWidth(80); ddSave.setPrefHeight(20);
				ddSave.setTranslateX(150);ddSave.setTranslateY(300);	
				
				ddOK.setOnAction(new EventHandler<ActionEvent>() {  //OK button in degree distribution window
					public void handle(ActionEvent event) {
						ddStage.close();
					}
				});
				
				ddSave.setOnAction(new EventHandler<ActionEvent>() {  //Save button in degree distribution window
					public void handle(ActionEvent event) {
						try {
				        FileChooser fileChooser = new FileChooser();
				        fileChooser.getExtensionFilters().addAll(
				        	new FileChooser.ExtensionFilter("TXT", "*.txt"),
				        	new FileChooser.ExtensionFilter("XLS", "*.xls")

				        );
				        Stage s = new Stage();
				        File file = fileChooser.showSaveDialog(s);
				        if (file.exists()) {
				        	file.delete();
				        	}
				        	file.createNewFile();

				        	FileOutputStream fos = new FileOutputStream(file);
				        	PrintWriter pw = new PrintWriter(fos);
				        	pw.write(network.DegDistr());
				        	pw.flush();
				        	fos.close();
				        	pw.close();
						} catch (Exception e) {}
					}
				});
				
				ddroot.getChildren().addAll(ddtext,ddtitle,ddcontent,ddOK,ddSave);	
				ddStage.setScene(ddscene);
				ddStage.show();
				}
		    });
			
			detail.setOnAction(new EventHandler<ActionEvent>() {  //detail button in main window
			public void handle(ActionEvent event) {
				Stage detailStage = new Stage();
				detailStage.setTitle("Details of Network"); //设置标题
				detailStage.getIcons().add(new Image("/icon/icon.png")); //设置图标
				detailStage.setWidth(350); //设置宽高
				detailStage.setHeight(370);
				detailStage.setResizable(false); //不允许改变窗口大小
				detailStage.initStyle(StageStyle.UNIFIED); //设置窗口样式
				detailStage.initModality(Modality.APPLICATION_MODAL);
				Group detailroot = new Group();
				Scene detailscene = new Scene(detailroot);
				GridPane detailgridpane = new GridPane();
				detailgridpane.setPrefWidth(350);
				detailgridpane.setPrefHeight(370);
				detailgridpane.setBackground(new Background(new BackgroundFill(Paint.valueOf("#F5F5F5"), null, null)));

				detailroot.getChildren().addAll(detailgridpane);
				
				Text nodename = new Text(0,0,"Node 1: \t\t Node 2:");
				nodename.setFont(Font.font(null,FontWeight.BLACK,13));
				nodename.setTranslateX(140);nodename.setTranslateY(30);
				
				Text interaction = new Text(0,0,"Interactions ->");
				interaction.setFont(Font.font(null,FontWeight.BLACK,13));
				interaction.setTranslateX(28);interaction.setTranslateY(56);
				
				TextArea ta = new TextArea();
				ta.setPrefWidth(180);ta.setPrefHeight(250);
				ta.setTranslateX(130);ta.setTranslateY(40);
				
				String content = new String();
				for(Edge x : network.EdgeList) {
					content = content + x.getNode1().getName() + "\t\t   " + x.getNode2().getName() + "\n";
				}
				ta.setText(content);
				
				Button detailOK = new Button("OK");
				detailOK.setPrefWidth(80); detailOK.setPrefHeight(20);
				detailOK.setTranslateX(130);detailOK.setTranslateY(300);	
				
				detailOK.setOnAction(new EventHandler<ActionEvent>() {  //OK button in detail window
					public void handle(ActionEvent event) {
						detailStage.close();
					}
				});
				
				detailgridpane.getChildren().add(ta);
				detailroot.getChildren().addAll(nodename,interaction,detailOK);	
				detailStage.setScene(detailscene);
				detailStage.show();
				}
		    });
			
			savenew.setOnAction(new EventHandler<ActionEvent>() {  //save button in main window
			public void handle(ActionEvent event) {
				try {
			        FileChooser fileChooser = new FileChooser();
			        fileChooser.getExtensionFilters().addAll(
			        	new FileChooser.ExtensionFilter("TXT", "*.txt")
			        );
			        Stage s = new Stage();
			        File file = fileChooser.showSaveDialog(s);
			        if (file.exists()) {
			        	file.delete();
			        	}
			        	file.createNewFile();
			        	FileOutputStream fos = new FileOutputStream(file);
			        	PrintWriter pw = new PrintWriter(fos);
				        String outputnet = new String();
				        for(Edge x : network.EdgeList) {
				        	outputnet = outputnet + x.getNode1().getName() + "\t" + x.getNode2().getName() + "\n";
				        }
			        	pw.write(outputnet);
			        	pw.flush();
			        	fos.close();
			        	pw.close();
					} catch (Exception e) {}
				}
		    });
			
			Stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
