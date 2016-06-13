package com.sadiqmdasif.messmanager;

/**
 * Created by Belal on 10/24/2015.
 */
public class Config_DBConnection {
    //Address of our scripts of the CRUD
    public static final String URL_ADD="http://sadiqmdasif.net16.net/billsplit/addMess.php";
    public static final String URL_ADD_MEMBER_TO_MESS="http://sadiqmdasif.net16.net/billsplit/addMemberToMess.php";

    public static final String URL_GET_USERS = "http://sadiqmdasif.net16.net/billsplit/getAllUser.php";
    public static final String URL_GET_MESS = "http://sadiqmdasif.net16.net/billsplit/getAllMess.php";

    public static final String URL_GET_EMP = "http://sadiqmdasif.net16.net/billsplit/getEmp.php?id=";
    public static final String URL_UPDATE_EMP = "http://sadiqmdasif.net16.net/billsplit/updateEmp.php";
    public static final String URL_DELETE_EMP = "http://sadiqmdasif.net16.net/billsplit/deleteEmp.php?id=";

    //Keys that will be used to send the request to php scripts
    public static final String KEY_EMP_ID = "id";
    public static final String KEY_EMP_NAME = "name";
    public static final String KEY_EMP_DESG = "desg";
    public static final String KEY_EMP_SAL = "salary";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_DESG = "desg";
    public static final String TAG_SAL = "salary";
    public static final String TAG_MEMBER = "member";
    public static final String TAG_MESSID = "messID";

    //employee id to pass with intent
    public static final String EMP_ID = "emp_id";
}
