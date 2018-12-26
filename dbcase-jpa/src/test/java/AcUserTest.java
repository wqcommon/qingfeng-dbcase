import com.alibaba.fastjson.JSONObject;
import com.dbcase.MainApplication;
import com.dbcase.entity.AcUserEntity;
import com.dbcase.reposity.AcUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.parser.Entity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
//@ActiveProfiles
public class AcUserTest {

    @Autowired
    private AcUserRepository acUserRepository;

    @Test
    public void testSaveAcUser(){
        AcUserEntity acUserEntity = new AcUserEntity();
        acUserEntity.setAge(12);
        acUserEntity.setId(3);
        acUserEntity.setName("wq003");
        Map<String,Object> jsonMap = new HashMap<>();
        jsonMap.put("username","wq003");
        jsonMap.put("address","address003");
        acUserEntity.setInfo(jsonMap);

        Map<String,Object> info2Map = new HashMap<>();
        info2Map.put("key1","value1");
        info2Map.put("key2","value2");
        acUserEntity.setInfo2(JSONObject.toJSONString(info2Map));

        AcUserEntity e = acUserRepository.save(acUserEntity);
        System.out.println(e);
    }

    @Test
    @Transactional
    public void testQueryAcUser(){
        AcUserEntity entity = acUserRepository.getOne(1);
        System.out.println("1==================" + JSONObject.toJSONString(entity));

        AcUserEntity queryEntity = new AcUserEntity();
        queryEntity.setName("wq001");
        queryEntity.setAge(10);
        List<AcUserEntity> entityList = acUserRepository.findAll(Example.of(queryEntity));
        System.out.println("2====================" + JSONObject.toJSONString(entityList));

        List<AcUserEntity> entityList2 = acUserRepository.query("wq001",10);
        System.out.println("3===================" + JSONObject.toJSONString(entityList2));


        List<AcUserEntity> entityList3 = acUserRepository.queryByAddress("address003");
        System.out.println("4===================" + JSONObject.toJSONString(entityList3));


    }
}
