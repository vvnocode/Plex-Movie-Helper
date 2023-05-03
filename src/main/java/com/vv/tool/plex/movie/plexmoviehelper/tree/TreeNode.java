package com.vv.tool.plex.movie.plexmoviehelper.tree;

import lombok.Getter;
import lombok.Setter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 实现树结构
 **/
@Getter
@Setter
public class TreeNode<T> implements Iterable<TreeNode<T>> {

    /**
     * 树节点
     */
    T data;

    /**
     * 父节点，根没有父节点
     */
    TreeNode<T> parent;

    /**
     * 子节点，叶子节点没有子节点
     */
    List<TreeNode<T>> children;

    /**
     * 保存了当前节点及其所有子节点，方便查询
     */
    List<TreeNode<T>> elementsIndex;

    /**
     * 构造函数
     *
     * @param data
     */
    public TreeNode(T data) {
        this.data = data;
        this.children = new LinkedList<>();
        this.elementsIndex = new LinkedList<>();
        this.elementsIndex.add(this);
    }

    /**
     * 判断是否为根：根没有父节点
     *
     * @return
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * 判断是否为叶子节点：子节点没有子节点
     *
     * @return
     */
    public boolean isLeaf() {
        return children.size() == 0;
    }

    /**
     * 添加一个子节点
     *
     * @param child
     * @return 返回子节点
     */
    public TreeNode<T> addChild(T child) {
        TreeNode<T> childNode = new TreeNode<T>(child);
        childNode.parent = this;
        this.children.add(childNode);
        this.registerChildForSearch(childNode);
        return childNode;
    }

    /**
     * 获取当前节点的层
     *
     * @return
     */
    public int getLevel() {
        if (this.isRoot()) {
            return 0;
        } else {
            return parent.getLevel() + 1;
        }
    }

    /**
     * 递归为当前节点以及当前节点的所有父节点增加新的节点
     *
     * @param node
     */
    private void registerChildForSearch(TreeNode<T> node) {
        elementsIndex.add(node);
        if (parent != null) {
            parent.registerChildForSearch(node);
        }
    }

    /**
     * 从当前节点及其所有子节点中搜索某节点
     *
     * @param cmp
     * @return
     */
    public TreeNode<T> findTreeNode(Comparable<T> cmp) {
        for (TreeNode<T> element : this.elementsIndex) {
            T elData = element.data;
            if (cmp.compareTo(elData) == 0)
                return element;
        }

        return null;
    }

    /**
     * 获取当前节点的迭代器
     *
     * @return
     */
    public Iterator<TreeNode<T>> iterator() {
        TreeNodeIterator<T> iterator = new TreeNodeIterator<T>(this);
        return iterator;
    }

    @Override
    public String toString() {
        return data != null ? data.toString() : "[tree data null]";
    }

}
