<template>
  <div class="app-container">
    <el-table v-loading="listLoading" :data="list" row-key="id" border fit highlight-current-row style="width: 100%">
      <el-table-column align="center" label="名称" width="65">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="负责人">
        <template slot-scope="scope">
          <span>{{ scope.row.author }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="状态">
        <template slot-scope="{row}">
          <span>{{ row.status | statusFilter }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" width="180px" label="调度地址">
        <template slot-scope="scope">
          <span>{{ scope.row.url }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" width="100px" label="调度表达式">
        <template slot-scope="scope">
          <span>{{ scope.row.cornExpression }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="开启时间" width="180px">
        <template slot-scope="scope">
          <span>{{ scope.row.startTime }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="结束时间" width="180px">
        <template slot-scope="scope">
          <span>{{ scope.row.endTime }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="描述">
        <template slot-scope="scope">
          <span>{{ scope.row.description }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="创建时间">
        <template slot-scope="scope">
          <span>{{ scope.row.gmtCreated }}</span>
        </template>
      </el-table-column>

      <el-table-column align="left" label="操作" min-width="200px">
        <template slot-scope="scope">
          <el-button v-if="scope.row.status === 0" type="primary" size="mini" @click="start(scope.row.id)">开启</el-button>
          <el-button v-if="scope.row.status === 1" type="danger" size="mini" @click="stop(scope.row.id)">停止</el-button>
          <el-button v-if="scope.row.status === 2" type="success" size="mini" @click="resume(scope.row.id)">恢复</el-button>
          <el-button v-if="scope.row.status === 1" type="warning" size="mini" @click="pause(scope.row.id)">暂停</el-button>
          <router-link v-if="scope.row.status === 0" :to="'/quartz/edit/' + scope.row.id ">
            <el-button v-if="scope.row.status === 0" type="success" size="mini">
              编辑
            </el-button>
          </router-link>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      :current-page="listQuery.pageIndex"
      :page-sizes="[10, 20, 50, 80]"
      :page-size="listQuery.pageSize"
      :total="total"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"/>
  </div>
</template>

<script>
import { fetchList, pause, resume, stop, start } from '@/api/quartz'

export default {
  name: 'Index',
  filters: {
    statusFilter(status) {
      const statusMap = {
        0: '停止',
        1: '运行中',
        2: '暂停'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      listLoading: false,
      list: undefined,
      total: 0,
      listQuery: {
        pageSize: 20,
        pageIndex: 1
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchList(this.listQuery).then(response => {
        this.list = response.data.data.list
        this.total = response.data.data.total
        this.listLoading = false
      })
    },
    handleSizeChange(val) {
      this.listQuery.pageSize = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.listQuery.pageIndex = val
      this.getList()
    },
    pause(val) {
      this.$confirm('是否暂时当前任务?', {
        title: '提示',
        showCancelButton: true,
        confirmButtonText: '暂停',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        pause(val).then(response => {
          this.$message({
            message: response.data.message,
            duration: 5 * 1000
          })
          this.getList()
        })
      })
    },
    resume(val) {
      this.$confirm('是否恢复当前任务?', {
        title: '提示',
        showCancelButton: true,
        confirmButtonText: '恢复',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        resume(val).then(response => {
          this.$message({
            message: response.data.message,
            duration: 5 * 1000
          })
          this.getList()
        })
      })
    },
    start(val) {
      this.$confirm('是否启动当前任务?', {
        title: '提示',
        showCancelButton: true,
        confirmButtonText: '启动',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        start(val).then(response => {
          this.$message({
            message: response.data.message,
            duration: 5 * 1000
          })
          this.getList()
        })
      })
    },
    stop(val) {
      this.$confirm('是否停止当前任务?', {
        title: '提示',
        showCancelButton: true,
        confirmButtonText: '停止',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        stop(val).then(response => {
          this.$message({
            message: response.data.message,
            duration: 5 * 1000
          })
          this.getList()
        })
      })
    }
  }
}
</script>

<style scoped>

</style>
