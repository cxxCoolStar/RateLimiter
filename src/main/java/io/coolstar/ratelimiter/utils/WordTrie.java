package io.coolstar.ratelimiter.utils;

/**
 * @author chenxingxing
 * @date 2022/12/14 10:00 下午
 *
 * 实现一个简单的trie
 */
public class WordTrie {

    private Node root  = new Node();

    /**
     * 判断单词是否存在
     *
     * @return
     *
     */
    public boolean isExisted(String word) {
        // 1.将word分解为chars
        char [] chars = word.toCharArray();
        Node cur = root;
        // 2.遍历chars，看看是否存在，如果存在，则跳到下一个单词和下一个子节点
        for (int i = 0; i < chars.length; i++) {
            Node[] nodes = cur.nodes;
            char c = chars[i];
            int idx = c - 'A';
            if (nodes[idx] == null || nodes[idx].aChar != c) {
                return false;
            }
            //跳转到下一个节点
            cur = nodes[idx];
        }
        return true;
    }

    /**
     * 保存一个单词
     */
    public  void saveWord(String word) {
        // 1.将word分解为chars
        char [] chars = word.toCharArray();
        Node cur = root;
        // 2.遍历chars，看看是否存在，如果存在，则跳到下一个单词和下一个子节点
        for (int i = 0; i < chars.length; i++) {
            Node[] nodes = cur.nodes;
            char c = chars[i];
            int idx = c - 'A';
            nodes[idx] = new Node();
            nodes[idx].aChar = c;
            //跳转到下一个节点
            cur = nodes[idx];
        }
    }

     class Node {
        char aChar;
        Node[] nodes = new Node[58];

    }

    public static void main(String[] args) {
        WordTrie wordTrie = new WordTrie();
        wordTrie.saveWord("apple");
        System.out.println(wordTrie.isExisted("hello"));
    }
}
