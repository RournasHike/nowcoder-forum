package com.nowcoder.community.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 敏感词过滤器
 * @author Alex
 * @version 1.0
 * @date 2022/2/6 18:03
 */
@Component
@Slf4j
public class SensitiveWordFilter {

    private static final String REPLACEMENT = "***";

    /**
     * 字典树根节点
     */
    private TrieNode root = new TrieNode();

    /**
     * @Description 初始化前缀树
     * @param
     * @return
     * @throws
     */
    @PostConstruct
    public void init(){
        try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-word.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        ) {
            String keyword;
            while ((keyword = bufferedReader.readLine())!=null){
                this.addKeyWord(keyword);
            }
        } catch (Exception e) {
            log.error("加载敏感词文件失败:{}",e.getMessage());
        }

    }

    /**
     * 把敏感词添加到前缀树中
     * @param keyword
     */
    private void addKeyWord(String keyword) {
        TrieNode tempNode = root;
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);
            if(subNode==null){
                // 初始化子节点，挂到当前节点下
                subNode = new TrieNode();
                tempNode.addSubNode(c,subNode);
            }
            // 当前节点指向子节点，进入下一轮循环
            tempNode = subNode;

            // 设置结束标识
            if(i==keyword.length()-1){
                tempNode.setKeyWordEnd(true);
            }
        }
    }

    /**
     * @Description 过滤敏感词
     * @param text 待过滤的文本
     * @return  过滤后的文本
     * @throws
     */
    public String filterSensitiveWords(String text){
        if (StringUtils.isBlank(text)) {
            return null;
        }

        // 声明三个指针
        // 指针1
        TrieNode tempNode = root;

        // 指针2
        int begin = 0;

        // 指针3
        int position = 0;

        // StringBuilder 暂存经过敏感词过滤后的文本
        StringBuilder sb = new StringBuilder();
        while (position<text.length()){
            char c = text.charAt(position);
            // 跳过特殊符号
            if(isSymbol(c)){
                // 若指针1处于根节点，将此符号计入结果，让指针2向下走一步
                if(tempNode == root){
                    sb.append(c);
                    begin++;
                }
                // 无论符号在开头或者中间，指针3都向下走一步
                position++;
                continue;
            }

            // 检查下级节点
            tempNode = tempNode.getSubNode(c);
            if(tempNode == null){
                // 以begin开头的字符串不是敏感词
                sb.append(text.charAt(begin));
                // 进入下一个位置
                position = ++begin;
                // 指针1(树形指针)只想root节点
                tempNode = root;
            }else if(tempNode.isKeyWordEnd()){
                // 发现敏感词,将begin~position的字符串替换掉
                sb.append(REPLACEMENT);
                // 进入下一个位置
                begin = ++position;
                // 重新执行根节点
                tempNode = root;
            }else{
                // 检查下一个字符
                position++;
            }
        }

        // 将最后一批字符计入结果
        sb.append(text.substring(begin));
        return sb.toString();
    }

    /**
     * @Description 判断是否为符号
     * @param c 传入字符
     * @return  true/false
     * @throws
     */
    private boolean isSymbol(Character c){
        // 0x2E80~0x9FFF是东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }

    /**
     * 前缀树/字典树 节点定义
     */
    private class TrieNode{
        /**
         * 关键词结束标识：判断是否为敏感词
         */
        private boolean isKeyWordEnd = false;

        /**
         * 当前节点子节点:(key是下级字符,value是下级节点)
         */
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public boolean isKeyWordEnd() {
            return isKeyWordEnd;
        }

        public void setKeyWordEnd(boolean keyWordEnd) {
            isKeyWordEnd = keyWordEnd;
        }

        public Map<Character, TrieNode> getSubNodes() {
            return subNodes;
        }


        public void setSubNodes(Map<Character, TrieNode> subNodes) {
            this.subNodes = subNodes;
        }

        /**
         * 添加子节点
         * @return
         */
        public void addSubNode(Character character,TrieNode trieNode){
            subNodes.put(character,trieNode);
        }

        /**
         * 根据key获取子节点
         * @param key
         * @return
         */
        public TrieNode getSubNode(Character key){
            return subNodes.get(key);
        }
    }

}
