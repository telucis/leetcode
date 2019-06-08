package medium.design;

/**
 * @author karl.wy
 * @date 2019/05/17
 *
 * 设计循环双端队列
 *
    设计实现双端队列。
    你的实现需要支持以下操作：

    MyCircularDeque(k)：构造函数,双端队列的大小为k。
    insertFront()：将一个元素添加到双端队列头部。 如果操作成功返回 true。
    insertLast()：将一个元素添加到双端队列尾部。如果操作成功返回 true。
    deleteFront()：从双端队列头部删除一个元素。 如果操作成功返回 true。
    deleteLast()：从双端队列尾部删除一个元素。如果操作成功返回 true。
    getFront()：从双端队列头部获得一个元素。如果双端队列为空，返回 -1。
    getRear()：获得双端队列的最后一个元素。 如果双端队列为空，返回 -1。
    isEmpty()：检查双端队列是否为空。
    isFull()：检查双端队列是否满了。
    示例：

    MyCircularDeque circularDeque = new MycircularDeque(3); // 设置容量大小为3
    circularDeque.insertLast(1);			        // 返回 true
    circularDeque.insertLast(2);			        // 返回 true
    circularDeque.insertFront(3);			        // 返回 true
    circularDeque.insertFront(4);			        // 已经满了，返回 false
    circularDeque.getRear();  				// 返回 2
    circularDeque.isFull();				        // 返回 true
    circularDeque.deleteLast();			        // 返回 true
    circularDeque.insertFront(4);			        // 返回 true
    circularDeque.getFront();				// 返回 4



    提示：

    所有值的范围为 [1, 1000]
    操作次数的范围为 [1, 1000]
    请不要使用内置的双端队列库。

 */
public class design_circular_deque_641 {

    class MyCircularDeque {
        int size;
        int k;
        DoubleListNode head;
        DoubleListNode tail;
        /** Initialize your data structure here. Set the size of the deque to be k. */
        public MyCircularDeque(int k) {
            head = new DoubleListNode(-1);
            tail = new DoubleListNode(-1);
            head.next = tail;
            tail.pre = head;
            this.k = k;
            this.size=0;
            System.out.println(this.k);
        }

        /** Adds an item at the front of Deque. Return true if the operation is successful. */
        public boolean insertFront(int value) {
            if (size==k) return false;
            DoubleListNode node = new DoubleListNode(value);
            node.pre = head;
            node.next = head.next;
            head.next.pre = node;
            head.next = node;
            size++;
            return true;
        }

        /** Adds an item at the rear of Deque. Return true if the operation is successful. */
        public boolean insertLast(int value) {
            if (size==k) return false;
            DoubleListNode node = new DoubleListNode(value);
            node.next = tail;
            node.pre = tail.pre;
            tail.pre.next = node;
            tail.pre = node;
            size++;
            return true;
        }

        /** Deletes an item from the front of Deque. Return true if the operation is successful. */
        public boolean deleteFront() {
            if (size==0) return false;
            head.next.next.pre = head;
            head.next = head.next.next;
            size--;
            return true;
        }

        /** Deletes an item from the rear of Deque. Return true if the operation is successful. */
        public boolean deleteLast() {
            if (size==0) return false;
            tail.pre.pre.next = tail;
            tail.pre = tail.pre.pre;
            size--;
            return true;
        }

        /** Get the front item from the deque. */
        public int getFront() {
            return head.next.val;
        }

        /** Get the last item from the deque. */
        public int getRear() {
            return tail.pre.val;
        }

        /** Checks whether the circular deque is empty or not. */
        public boolean isEmpty() {
            return size==0;
        }

        /** Checks whether the circular deque is full or not. */
        public boolean isFull() {
            return size==k;
        }
    }
    private class DoubleListNode {
        DoubleListNode pre;
        DoubleListNode next;
        int val;

        public DoubleListNode(int val) {
            this.val = val;
        }
    }
}
