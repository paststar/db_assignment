package com.title.DAO;

import com.title.DTO.titleDTO;
import com.title.DTO.userDTO;
import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class titleDAO {

    @Autowired
    SqlSessionTemplate sqlSession;

    String namespace="com.title";

    public List<titleDTO> selectMovie(String userId){
        List<titleDTO> titleDTOList=null;

        titleDTOList=sqlSession.selectList(namespace+".selectMovie",userId);

        return titleDTOList;
    }

    public List<titleDTO> selectMovie2(String userId){
        List<titleDTO> titleDTOList=null;

        titleDTOList=sqlSession.selectList(namespace+".selectMovie2",userId);

        return titleDTOList;
    }

    public List<titleDTO> selectMovie3(String userId){
        List<titleDTO> titleDTOList=null;

        titleDTOList=sqlSession.selectList(namespace+".selectMovie3",userId);

        return titleDTOList;
    }

    public List<titleDTO> selectMovie4(String userId){
        List<titleDTO> titleDTOList=null;

        titleDTOList=sqlSession.selectList(namespace+".selectMovie4",userId);

        return titleDTOList;
    }

    public List<titleDTO> selectMovie5(String userId){
        List<titleDTO> titleDTOList=null;

        titleDTOList=sqlSession.selectList(namespace+".selectMovie5",userId);

        return titleDTOList;
    }

    public userDTO selectUser(String userId){

        userDTO nowUser=null;

        nowUser=sqlSession.selectOne(namespace+".selectUser",userId);

        return nowUser;
    }

}
