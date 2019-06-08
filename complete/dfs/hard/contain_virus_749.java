package complete.dfs.hard;

import java.util.Arrays;

/**
 * @author karl.wy
 * @date 2019/05/26
 *
 * 隔离病毒
 *
    病毒扩散得很快，现在你的任务是尽可能地通过安装防火墙来隔离病毒。

    假设世界由二维矩阵组成，0 表示该区域未感染病毒，而 1 表示该区域已感染病毒。可以在任意 2 个四方向相邻单元之间的共享边界上安装一个防火墙（并且只有一个防火墙）。

    每天晚上，病毒会从被感染区域向相邻未感染区域扩散，除非被防火墙隔离。现由于资源有限，每天你只能安装一系列防火墙来隔离其中一个被病毒感染的区域（一个区域或连续的一片区域），且该感染区域对未感染区域的威胁最大且保证唯一。

    你需要努力使得最后有部分区域不被病毒感染，如果可以成功，那么返回需要使用的防火墙个数; 如果无法实现，则返回在世界被病毒全部感染时已安装的防火墙个数。



    示例 1：

    输入: grid =
    [[0,1,0,0,0,0,0,1],
    [0,1,0,0,0,0,0,1],
    [0,0,0,0,0,0,0,1],
    [0,0,0,0,0,0,0,0]]
    输出: 10
    说明:
    一共有两块被病毒感染的区域: 从左往右第一块需要 5 个防火墙，同时若该区域不隔离，晚上将感染 5 个未感染区域（即被威胁的未感染区域个数为 5）;
    第二块需要 4 个防火墙，同理被威胁的未感染区域个数是 4。因此，第一天先隔离左边的感染区域，经过一晚后，病毒传播后世界如下:
    [[0,1,0,0,0,0,1,1],
    [0,1,0,0,0,0,1,1],
    [0,0,0,0,0,0,1,1],
    [0,0,0,0,0,0,0,1]]
    第二题，只剩下一块未隔离的被感染的连续区域，此时需要安装 5 个防火墙，且安装完毕后病毒隔离任务完成。
    示例 2：

    输入: grid =
    [[1,1,1],
    [1,0,1],
    [1,1,1]]
    输出: 4
    说明:
    此时只需要安装 4 面防火墙，就有一小区域可以幸存，不被病毒感染。
    注意不需要在世界边界建立防火墙。


    示例 3:

    输入: grid =
    [[1,1,1,0,0,0,0,0,0],
    [1,0,1,0,1,1,1,1,1],
    [1,1,1,0,0,0,0,0,0]]
    输出: 13
    说明:
    在隔离右边感染区域后，隔离左边病毒区域只需要 2 个防火墙了。


    说明:

    grid 的行数和列数范围是 [1, 50]。
    grid[i][j] 只包含 0 或 1 。
    题目保证每次选取感染区域进行隔离时，一定存在唯一一个对未感染区域的威胁最大的区域。



 */
public class contain_virus_749 {

    private static final int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    public int containVirus(int[][] grid) {
        int res=0, tmpRes;
        while (true) {
            tmpRes = findMaxVirus(grid);
            res += tmpRes;
            if (tmpRes==0) break;
        }
        return res;
    }
    private int findMaxVirus(int[][] grid) {
        int needNum=0, maxVirus=0, maxTarRow=0, maxTarCol=0;
        int rowSize=grid.length, colSize=grid[0].length;
        boolean[][] visited = new boolean[rowSize][colSize];
        //
        for (int row=0; row<rowSize; ++row) {
            for (int col=0; col<colSize; ++col) {
                if (grid[row][col]==1 && !visited[row][col]) {
                    boolean[][] tempVisited = new boolean[rowSize][colSize];
                    int virusNum = calVirus(grid, visited, tempVisited, row, col);
                    if (virusNum > maxVirus) {
                        maxVirus = virusNum;
                        maxTarRow = row;
                        maxTarCol = col;
                    }
                }
            }
        }
        if (maxVirus != 0) {
            //
            needNum = floorFill(grid, maxTarRow, maxTarCol);
            //
            for (boolean[] arr : visited) Arrays.fill(arr, false);
            for (int row=0; row<rowSize; ++row) {
                for (int col=0; col<colSize; ++col) {
                    if (grid[row][col] == 1 && !visited[row][col]) {
                        virusExpand(grid, visited, row, col);
                    }
                }
            }
        }
        return needNum;
    }
    // 计算感染区[row, col]大小
    private int calVirus(int[][] grid, boolean[][] visited, boolean[][] tempVisited, int row, int col) {
        visited[row][col] = tempVisited[row][col] = true;
        int rowSize=grid.length, colSize=grid[0].length, tempRes=0;
        for (int[] dir : dirs) {
            int i = row+dir[0], j = col+dir[1];
            if (i<0 || j<0 || i>=rowSize || j>=colSize || tempVisited[i][j] || grid[i][j]==-1) continue;
            if (grid[i][j]==0) {
                tempRes+=1;
                tempVisited[i][j]=true;
            } else {
                tempRes+=calVirus(grid, visited, tempVisited, i, j);
            }
        }
        return tempRes;
    }
    // 将感染区[row, col]隔离
    private int floorFill(int[][] grid, int row, int col) {
        grid[row][col]=-1;
        int m=grid.length, n=grid[0].length, tempRes=0;
        for (int[] dir : dirs) {
            int i=row+dir[0], j=col+dir[1];
            if (i<0 || j<0 || i>=m || j>=n || grid[i][j]==-1) continue;
            if (grid[i][j]==1) tempRes+=floorFill(grid, i, j);
            else tempRes+=1;
        }
        return tempRes;
    }
    // 将病毒区[row, col]扩张
    private void virusExpand(int[][] grid, boolean[][] visited, int row, int col) {
        visited[row][col] = true;
        int m=grid.length, n=grid[0].length;
        for (int[] dir : dirs) {
            int i=row+dir[0], j=col+dir[1];
            if (i<0 || j<0 || i>=m || j>=n || visited[i][j] || grid[i][j]==-1) continue;
            if (grid[i][j]==0) {
                grid[i][j]=1;
                visited[i][j]=true;
            } else {
                virusExpand(grid, visited, i, j);
            }
        }
    }
}
