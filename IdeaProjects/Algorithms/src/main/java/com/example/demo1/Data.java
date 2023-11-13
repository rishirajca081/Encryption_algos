package com.example.demo1;
//package com.example.prac1;


class Data{
    private int sno;
    private String path;
//    private Button deleteButton;

    Data(int sno, String path){
        this.sno = sno;
        this.path = path;
//        this.deleteButton = new Button("Delete");
    }

    public int getSno(){
        return sno;
    }

    public String getPath(){
        return path;
    }

    public void setSno(int Sno){
        this.sno = Sno;
    }

    public void setPath(String Path){
        this.path = Path;
    }

//    public Button getButton(){
//        return deleteButton;
//    }

//    public void setButton(Button button){
//        this.deleteButton = button;
//    }
}
