<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="逻辑文件唯一标识" prop="fileId">
      <el-input v-model="dataForm.fileId" placeholder="逻辑文件唯一标识"></el-input>
    </el-form-item>
    <el-form-item label="逻辑文件名" prop="filename">
      <el-input v-model="dataForm.filename" placeholder="逻辑文件名"></el-input>
    </el-form-item>
    <el-form-item label="文件操作者标识" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="文件操作者标识"></el-input>
    </el-form-item>
    <el-form-item label="文件操作者名称" prop="username">
      <el-input v-model="dataForm.username" placeholder="文件操作者名称"></el-input>
    </el-form-item>
    <el-form-item label="文件操作" prop="operation">
      <el-input v-model="dataForm.operation" placeholder="文件操作"></el-input>
    </el-form-item>
    <el-form-item label="日志发生时间" prop="logTime">
      <el-input v-model="dataForm.logTime" placeholder="日志发生时间"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          fileId: '',
          filename: '',
          userId: '',
          username: '',
          operation: '',
          logTime: ''
        },
        dataRule: {
          fileId: [
            { required: true, message: '逻辑文件唯一标识不能为空', trigger: 'blur' }
          ],
          filename: [
            { required: true, message: '逻辑文件名不能为空', trigger: 'blur' }
          ],
          userId: [
            { required: true, message: '文件操作者标识不能为空', trigger: 'blur' }
          ],
          username: [
            { required: true, message: '文件操作者名称不能为空', trigger: 'blur' }
          ],
          operation: [
            { required: true, message: '文件操作不能为空', trigger: 'blur' }
          ],
          logTime: [
            { required: true, message: '日志发生时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/disk/fileoplog/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.fileId = data.fileOpLog.fileId
                this.dataForm.filename = data.fileOpLog.filename
                this.dataForm.userId = data.fileOpLog.userId
                this.dataForm.username = data.fileOpLog.username
                this.dataForm.operation = data.fileOpLog.operation
                this.dataForm.logTime = data.fileOpLog.logTime
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/disk/fileoplog/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'fileId': this.dataForm.fileId,
                'filename': this.dataForm.filename,
                'userId': this.dataForm.userId,
                'username': this.dataForm.username,
                'operation': this.dataForm.operation,
                'logTime': this.dataForm.logTime
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
