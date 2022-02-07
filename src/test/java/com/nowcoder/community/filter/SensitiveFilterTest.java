package com.nowcoder.community.filter;

import com.nowcoder.community.CommunityApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Alex
 * @version 1.0
 * @date 2022/2/6 21:03
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SensitiveFilterTest {

    @Autowired
    private SensitiveWordFilter sensitiveWordFilter;

    @Test
    public void testSensitiveFilter(){
        String text = "这里可以赌博,可以吸毒,可以开票,可以babecue";
        text = sensitiveWordFilter.filterSensitiveWords(text);
        System.out.println(text);

    }
}
