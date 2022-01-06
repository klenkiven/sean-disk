<template>
  <el-dialog
    :title="!dataForm.labelId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="标签创建者ID" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="标签创建者ID"></el-input>
    </el-form-item>
    <el-form-item label="文件标签名" prop="labelName">
      <el-input v-model="dataForm.labelName" placeholder="文件标签名"></el-input>
    </el-form-item>
    <el-form-item label="是否属于系统标签" prop="isSystem">
      <el-input v-model="dataForm.isSystem" placeholder="是否属于系统标签"></el-input>
    </el-form-item>
    <el-form-item label="标签创建时间" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="标签创建时间"></el-input>
    </el-form-item>
    <el-form-item label="标签更新时间" prop="updateTime">
      <el-input v-model="dataForm.updateTime" placeholder="标签更新时间"></el-input>
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
          labelId: 0,
          userId: '',
          labelName: '',
          isSystem: '',
          createTime: '',
          updateTime: ''
        },
        dataRule: {
          userId: [
            { required: true, message: '标签创建者ID不能为空', trigger: 'blur' }
          ],
          labelName: [
            { required: true, message: '文件标签名不能为空', trigger: 'blur' }
          ],
          isSystem: [
            { required: true, message: '是否属于系统标签不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '标签创建时间不能为空', trigger: 'blur' }
          ],
          updateTime: [
            { required: true, message: '标签更新时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.labelId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.labelId) {
            this.$http({
              url: this.$http.adornUrl(`/disk/label/info/${this.dataForm.labelId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.label.userId
                this.dataForm.labelName = data.label.labelName
                this.dataForm.isSystem = data.label.isSystem
                this.dataForm.createTime = data.label.createTime
                this.dataForm.updateTime = data.label.updateTime
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
              url: this.$http.adornUrl(`/disk/label/${!this.dataForm.labelId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'labelId': this.dataForm.labelId || undefined,
                'userId': this.dataForm.userId,
                'labelName': this.dataForm.labelName,
                'isSystem': this.dataForm.isSystem,
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
