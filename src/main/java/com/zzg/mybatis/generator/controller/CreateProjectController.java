package com.zzg.mybatis.generator.controller;

import com.zzg.mybatis.generator.model.CreateProjectConfig;
import com.zzg.mybatis.generator.view.AlertUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Created by leosoft on 2018/9/12.
 */
public class CreateProjectController extends BaseFXController {
    private MainUIController mainUIController;
    private static final String FOLDER_NO_EXIST = "部分目录不存在，是否创建";
    @FXML
    private TextField projectFolderField;
    @FXML
    private TextField packageField;
    @FXML
    private TextField nameField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void cancel() {
        getDialogStage().close();
    }

    @FXML
    void createProject() {
        String s = validateConfig();
        if (!StringUtils.isEmpty(s)){
            AlertUtil.showErrorAlert(s);
            return;
        }
        try {
            CreateProjectConfig createProjectConfig = intConfig();
            String dirs = checkDirs(createProjectConfig);
            if ("".equals(dirs)) {
                return;
            }
            URL resource = this.getClass().getResource("/template/resources");
            URL pom = this.getClass().getResource("/template/pom.xml");
            copyFile(pom.getPath(),dirs);
            String packageName = createProjectConfig.getPackageName();
            String[] split = packageName.split("\\.");
            //替换/groupId/
            if (split.length>2) {
                replacePackage(dirs+"//pom.xml","/groupId/",split[0]+"."+split[1]);
            }
            else {
                AlertUtil.showWarnAlert("吔屎啦啊！包名的长度最少要2位！");
                return;
            }
            //替换/artifactId/
            replacePackage(dirs+"//pom.xml","/artifactId/",createProjectConfig.getProjectName());
            copyFolderFiles(new File(resource.getPath()),new File(dirs+"\\src\\main\\resources"));

            URL appyml = this.getClass().getResource("/template/resources/application.yml");
            replacePackage( copyFile(appyml.getPath(),dirs+"\\src\\main\\resources"),"<packageName>",createProjectConfig.getPackageName().replace('.','/'));


            StringBuilder stringBuilder = new StringBuilder();
            for (String packageStr : split) {
                  stringBuilder.append(packageStr);
                  stringBuilder.append("\\");
            }
            String packageFolder = stringBuilder.toString();
            packageFolder = dirs + "\\src\\main\\java\\" + packageFolder.substring(0, packageFolder.lastIndexOf("\\"));
            FileUtils.forceMkdir(new File(packageFolder));
            FileUtils.forceMkdir(new File(packageFolder+"\\dao\\mapper"));
            FileUtils.forceMkdir(new File(packageFolder+"\\dao\\mapping"));
            FileUtils.forceMkdir(new File(packageFolder+"\\common\\config"));
            FileUtils.forceMkdir(new File(packageFolder+"\\common\\exception"));
            FileUtils.forceMkdir(new File(packageFolder+"\\common\\utils"));
            FileUtils.forceMkdir(new File(packageFolder+"\\service\\impl"));
            FileUtils.forceMkdir(new File(packageFolder+"\\web\\aop"));
            FileUtils.forceMkdir(new File(packageFolder+"\\web\\controller"));
            FileUtils.forceMkdir(new File(packageFolder+"\\web\\filter"));
            FileUtils.forceMkdir(new File(packageFolder+"\\web\\view\\input"));
            FileUtils.forceMkdir(new File(packageFolder+"\\web\\view\\output"));

            URL application = this.getClass().getResource("/webconfig/Application.java");
            replacePackage( copyFile(application.getPath(),packageFolder),"/packageName/",createProjectConfig.getPackageName());

             //web下的替换
            URL webConfig = this.getClass().getResource("/webconfig/WebConfig.java");
            replacePackage( copyFile(webConfig.getPath(),packageFolder+"\\web"),"/packageName/",createProjectConfig.getPackageName());


            // web/controller
            URL baseController = this.getClass().getResource("/controller/BaseController.java");
            replacePackage( copyFile(baseController.getPath(),packageFolder+"\\web\\controller"),"/packageName/",createProjectConfig.getPackageName());
            URL homeController = this.getClass().getResource("/controller/HomeController.java");
            replacePackage( copyFile(homeController.getPath(),packageFolder+"\\web\\controller"),"/packageName/",createProjectConfig.getPackageName());
            URL userController = this.getClass().getResource("/controller/UserController.java");
            replacePackage( copyFile(userController.getPath(),packageFolder+"\\web\\controller"),"/packageName/",createProjectConfig.getPackageName());

            // web/aop
            URL authorizeWrapper = this.getClass().getResource("/aop/AuthorizeWrapper.java");
            replacePackage( copyFile(authorizeWrapper.getPath(),packageFolder+"\\web\\aop"),"/packageName/",createProjectConfig.getPackageName());
            URL logAdvice = this.getClass().getResource("/aop/LogAdvice.java");
            replacePackage( copyFile(logAdvice.getPath(),packageFolder+"\\web\\aop"),"/packageName/",createProjectConfig.getPackageName());


            // web/view//input
            URL authorizeBase = this.getClass().getResource("/view/input/AuthorizeBase.java");
            replacePackage( copyFile(authorizeBase.getPath(),packageFolder+"\\web\\view\\input"),"/packageName/",createProjectConfig.getPackageName());
            URL loginInput= this.getClass().getResource("/view/input/LoginInput.java");
            replacePackage( copyFile(loginInput.getPath(),packageFolder+"\\web\\view\\input"),"/packageName/",createProjectConfig.getPackageName());

            // web/view//output
//            URL loginMapper = this.getClass().getResource("/view/output/mapper/LoginMapper.java");
//            replacePackage( copyFile(loginMapper.getPath(),packageFolder+"\\web\\view\\output\\mapper"),"/packageName/",createProjectConfig.getPackageName());
            URL loginOutput= this.getClass().getResource("/view/output/LoginOutput.java");
            replacePackage( copyFile(loginOutput.getPath(),packageFolder+"\\web\\view\\output"),"/packageName/",createProjectConfig.getPackageName());


            // web/filter
            URL loginFilter = this.getClass().getResource("/filter/LoginFilter.java");
            replacePackage( copyFile(loginFilter.getPath(),packageFolder+"\\web\\filter"),"/packageName/",createProjectConfig.getPackageName());
            URL xssAndSqlFilter = this.getClass().getResource("/filter/XssAndSqlFilter.java");
            replacePackage( copyFile(xssAndSqlFilter.getPath(),packageFolder+"\\web\\filter"),"/packageName/",createProjectConfig.getPackageName());

            //service
            URL userService = this.getClass().getResource("/service/IUserService.java");
            replacePackage( copyFile(userService.getPath(),packageFolder+"\\service"),"/packageName/",createProjectConfig.getPackageName());
            URL userServiceImpl = this.getClass().getResource("/service/impl/UserServiceImpl.java");
            replacePackage( copyFile(userServiceImpl.getPath(),packageFolder+"\\service\\impl"),"/packageName/",createProjectConfig.getPackageName());


            // common/config
            URL constFile = this.getClass().getResource("/common/config/Const.java");
            replacePackage( copyFile(constFile.getPath(),packageFolder+"\\common\\config"),"/packageName/",createProjectConfig.getPackageName());

            // common/exception
            URL errorCode = this.getClass().getResource("/common/exception/ErrorCode.java");
            replacePackage( copyFile(errorCode.getPath(),packageFolder+"\\common\\exception"),"/packageName/",createProjectConfig.getPackageName());
            URL globalExcetionHandler = this.getClass().getResource("/common/exception/GlobalExcetionHandler.java");
            replacePackage( copyFile(globalExcetionHandler.getPath(),packageFolder+"\\common\\exception"),"/packageName/",createProjectConfig.getPackageName());
            URL responseData = this.getClass().getResource("/common/exception/ResponseData.java");
            replacePackage( copyFile(responseData.getPath(),packageFolder+"\\common\\exception"),"/packageName/",createProjectConfig.getPackageName());
            URL simpleErrorException = this.getClass().getResource("/common/exception/SimpleErrorException.java");
            replacePackage( copyFile(simpleErrorException.getPath(),packageFolder+"\\common\\exception"),"/packageName/",createProjectConfig.getPackageName());


            // common/utils
            URL commonUtil = this.getClass().getResource("/common/utils/CommonUtil.java");
            replacePackage( copyFile(commonUtil.getPath(),packageFolder+"\\common\\utils"),"/packageName/",createProjectConfig.getPackageName());
            URL dateFormart = this.getClass().getResource("/common/utils/DateFormart.java");
            replacePackage( copyFile(dateFormart.getPath(),packageFolder+"\\common\\utils"),"/packageName/",createProjectConfig.getPackageName());
            URL desPassword = this.getClass().getResource("/common/utils/DesPassword.java");
            replacePackage( copyFile(desPassword.getPath(),packageFolder+"\\common\\utils"),"/packageName/",createProjectConfig.getPackageName());
            URL numberUtil = this.getClass().getResource("/common/utils/NumberUtil.java");
            replacePackage( copyFile(numberUtil.getPath(),packageFolder+"\\common\\utils"),"/packageName/",createProjectConfig.getPackageName());
            URL requestUtil = this.getClass().getResource("/common/utils/RequestUtil.java");
            replacePackage( copyFile(requestUtil.getPath(),packageFolder+"\\common\\utils"),"/packageName/",createProjectConfig.getPackageName());
            URL stringUtil = this.getClass().getResource("/common/utils/StringUtil.java");
            replacePackage( copyFile(stringUtil.getPath(),packageFolder+"\\common\\utils"),"/packageName/",createProjectConfig.getPackageName());
            URL xssAndSqlHttpServletRequestWrapper = this.getClass().getResource("/common/utils/XssAndSqlHttpServletRequestWrapper.java");
            replacePackage( copyFile(xssAndSqlHttpServletRequestWrapper.getPath(),packageFolder+"\\common\\utils"),"/packageName/",createProjectConfig.getPackageName());

        }catch (Exception ex){
            ex.printStackTrace();
            AlertUtil.showInfoAlert("出错了！看log");
            return;
        }
        AlertUtil.showInfoAlert("生成完了！");

        return;

    }


    @FXML
    public void chooseProjectFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedFolder = directoryChooser.showDialog(getPrimaryStage());
        if (selectedFolder != null) {
            projectFolderField.setText(selectedFolder.getAbsolutePath());
        }
    }

    void setMainUIController(MainUIController mainUIController) {
        this.mainUIController = mainUIController;
    }

    public CreateProjectConfig intConfig(){
        CreateProjectConfig createProjectConfig = new CreateProjectConfig();
        createProjectConfig.setPackageName(packageField.getText());
        createProjectConfig.setProjectName(nameField.getText());
        createProjectConfig.setSaveUrl(projectFolderField.getText());
        return createProjectConfig;
    }

    private String validateConfig() {
        String projectFolder = projectFolderField.getText();
        if (StringUtils.isEmpty(projectFolder))  {
            return "项目目录不能为空";
        }
        if (StringUtils.isEmpty(packageField.getText()))  {
            return "包名不能为空";
        }
        if (StringUtils.isAnyEmpty(nameField.getText())) {
            return "项目名不能为空";
        }
        return null;
    }


    private String  checkDirs(CreateProjectConfig config) throws IOException {
       String  dirs = config.getSaveUrl()+"\\"+config.getProjectName();

       boolean haveNotExistFolder =false;
            File file = new File(dirs);
            FileUtils.deleteDirectory(file);
        if (!file.exists()) {
                haveNotExistFolder = true;
            }
        if (haveNotExistFolder) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(FOLDER_NO_EXIST);
            Optional<ButtonType> optional = alert.showAndWait();
            if (optional.isPresent()) {
                if (ButtonType.OK == optional.get()) {
                    try {

                            FileUtils.forceMkdir(new File(dirs+"\\src\\main\\java"));
                        return dirs;
                    } catch (Exception e) {
                        AlertUtil.showErrorAlert("创建目录失败，请检查目录是否是文件而非目录");
                        return "";
                    }
                } else {
                    return "";
                }
            }
        }
        return dirs;
    }


    /**
     * 复制单个文件
     * @param url   源文件路径
     * @param folder 复制到的目的文件夹
     */
     private String  copyFile(String url,String folder) {
         int bytesum = 0;
         int byteread = 0;
         File oldfile = new File(url);
         try {
             if (oldfile.exists()) { //文件存在时
                 FileInputStream inStream = new FileInputStream(oldfile); //读入原文件
                 FileOutputStream fs = new FileOutputStream(new File(folder + "/", oldfile.getName()));
                 byte[] buffer = new byte[5120];
                 while ((byteread = inStream.read(buffer)) != -1) {
                     bytesum += byteread; //字节数 文件大小
                     fs.write(buffer, 0, byteread);
                 }
                 inStream.close();
                 return folder + "/" + oldfile.getName();
             }
         } catch (Exception e) {
             AlertUtil.showErrorAlert("复制单个文件操作出错");
             e.printStackTrace();
         }
         return "";
     }

     public void copyFolderFiles(File src,File dest) throws IOException {
         if (src.isDirectory()) {
             if (!dest.exists()) {
                 dest.mkdir();
             }
             String files[] = src.list();
             for (String file : files) {
                 File srcFile = new File(src, file);
                 File destFile = new File(dest, file);
                 // 递归复制
                 copyFolderFiles(srcFile, destFile);
             }
         } else {
             InputStream in = new FileInputStream(src);
             OutputStream out = new FileOutputStream(dest);

             byte[] buffer = new byte[1024];

             int length;

             while ((length = in.read(buffer)) > 0) {
                 out.write(buffer, 0, length);
             }
             in.close();
             out.close();
         }
     }

     //替换包名
     private void replacePackage(String file,String tempStr,String repalceStr){
         try {
             BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(file))));//数据流读取文件

             StringBuffer strBuffer = new StringBuffer();
             for (String temp = null; (temp = bufReader.readLine()) != null; temp = null) {
                 if(temp.indexOf(tempStr) != -1){ //判断当前行是否存在想要替换掉的字符 -1表示存在
                     temp = temp.replace(tempStr, repalceStr);//替换为你想要的东东
                 }
                 strBuffer.append(temp);
                 strBuffer.append(System.getProperty("line.separator"));//行与行之间的分割
             }
             bufReader.close();
             PrintWriter printWriter = new PrintWriter(file);//替换后输出的文件位置
             printWriter.write(strBuffer.toString().toCharArray());
             printWriter.flush();
             printWriter.close();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }





    }
