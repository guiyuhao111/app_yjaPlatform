package cn.net.gxht.app.yjdPlatform.file.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/10/13.
 */
public class FileEntity {
    private Integer id;
    private List<String> path;
    private String clientName;

    public FileEntity(List<String> path, String clientName) {
        this.path = path;
        this.clientName = clientName;
    }

    public FileEntity() {
    }

    public List<String> getPath() {
        return path;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FileEntity{" +
                "id=" + id +
                ", path=" + path +
                ", clientName='" + clientName + '\'' +
                '}';
    }
}
