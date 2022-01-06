<template>
  <el-dialog
    :title="!dataForm.fileId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户ID" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户ID"></el-input>
    </el-form-item>
    <el-form-item label="文件名" prop="filename">
      <el-input v-model="dataForm.filename" placeholder="文件名"></el-input>
    </el-form-item>
    <el-form-item label="文件大小" prop="length">
      <el-input v-model="dataForm.length" placeholder="文件大小"></el-input>
    </el-form-item>
    <el-form-item label="文件类型" prop="type">
      <el-input v-model="dataForm.type" placeholder="文件类型"></el-input>
    </el-form-item>
    <el-form-item label="物理文件哈希" prop="physicalHash">
      <el-input v-model="dataForm.physicalHash" placeholder="物理文件哈希"></el-input>
    </el-form-item>
    <el-form-item label="文件版本" prop="version">
      <el-input v-model="dataForm.version" placeholder="文件版本"></el-input>
    </el-form-item>
    <el-form-item label="文件创建时间" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="文件创建时间"></el-input>
    </el-form-item>
    <el-form-item label="文件更新时间" prop="updateTime">
      <el-input v-model="dataForm.updateTime" placeholder="文件更新时间"></el-input>
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
          fileId: 0,
          userId: '',
          filename: '',
          length: '',
          type: '',
          physicalHash: '',
          version: '',
          createTime: '',
          updateTime: ''
        },
        dataRule: {
          userId: [
            { required: true, message: '用户ID不能为空', trigger: 'blur' }
          ],
          filename: [
            { required: true, message: '文件名不能为空', trigger: 'blur' }
          ],
          length: [
            { required: true, message: '文件大小不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '文件类型不能为空', trigger: 'blur' }
          ],
          physicalHash: [
            { required: true, message: '物理文件哈希不能为空', trigger: 'blur' }
          ],
          version: [
            { required: true, message: '文件版本不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '文件创建时间不能为空', trigger: 'blur' }
          ],
          updateTime: [
            { required: true, message: '文件更新时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.fileId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.fileId) {
            this.$http({
              url: this.$http.adornUrl(`/disk/file/info/${this.dataForm.fileId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.file.userId
                this.dataForm.filename = data.file.filename
                this.dataForm.length = data.file.length
                this.dataForm.type = data.file.type
                this.dataForm.physicalHash = data.file.physicalHash
                this.dataForm.version = data.file.version
                this.dataForm.createTime = data.file.createTime
                this.dataForm.updateTime = data.file.updateTime
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
              url: this.$http.adornUrl(`/disk/file/${!this.dataForm.fileId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'fileId': this.dataForm.fileId || undefined,
                'userId': this.dataForm.userId,
                'filename': this.dataForm.filename,
                'length': this.dataForm.length,
                'type': this.dataForm.type,
                'physicalHash': this.dataForm.physicalHash,
                'version': this.dataForm.version,
                'createTime': this.dataForm.createTime,
                'updateTime': this.dataForm.updateTime
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
