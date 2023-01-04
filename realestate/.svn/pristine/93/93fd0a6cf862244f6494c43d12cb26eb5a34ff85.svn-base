package cn.gtmap.realestate.exchange.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="mailto:jpzhong1994@gmail.com">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/9/9 17:00
 */
public class TreeNode<T> implements Iterable<TreeNode<T>>{

    /**
     * 树节点
     */
    public T data;

    /**
     * 父节点
     */
    public TreeNode<T> parent;

    /**
     * 子节点
     */
    public List<TreeNode<T>> children;

    /**
     * 保存当前节点及其所有子节点，方便查询
     */
    private List<TreeNode<T>> elementsIndex;

    /**
     * 构造函数
     * @param data
     */
    public TreeNode(T data) {
        this.data = data;
        this.children = new LinkedList<TreeNode<T>>();
        this.elementsIndex = new LinkedList<TreeNode<T>>();
        this.elementsIndex.add(this);
    }

    /**
     * 判断是否为跟节点: 根节点没有父节点
     * @return
     */
    public boolean isRoot(){
        return parent == null;
    }

    /**
     * 判断是否是叶子节点: 叶子节点没有子节点
     * @return
     */
    public boolean isLeaf(){
        return children.size() == 0;
    }

    /**
     * 添加子节点
     * @param children
     * @return
     */
    public TreeNode<T> addChildren(T children){
        TreeNode<T> childNode = new TreeNode<T>(children);
        childNode.parent = this;
        this.children.add(childNode);
//        this.registerChildForSearch(childNode);
        return childNode;
    }

    /**
     * 获取当前节点的层数
     * @return
     */
    public int getLevel(){
        if(this.isRoot()){
            return 0;
        }else{
            return parent.getLevel() + 1;
        }
    }

    /**
     * 获取当前节点的迭代器
     * @return
     */
    @Override
    public Iterator<TreeNode<T>> iterator() {
        TreeNodeIterator<T> iterator = new TreeNodeIterator<T>(this);
        return iterator;
    }
}



