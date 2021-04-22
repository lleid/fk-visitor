<template>
  <div class="step2-wrapper">
    <div class="tips">我们将为您拍摄照片，请将脸部置于相框内</div>
    <div class="take-photo">
      <div class="column2">
        <video ref="video" id="video" width="400"></video>
      </div>
      <div class="column1" @click="handleClick">
        <div class="take">
          <c-icon type="fv-paizhao"></c-icon>
        </div>
      </div>
      <div class="column2 photo">
        <img :src="imageUrl" class="avatar" width="100%" height="100%" v-if="imageUrl" />
      </div>
    </div>
  </div>
</template>

<script>
// eslint-disable-next-line no-unused-vars
import adapter from 'webrtc-adapter'
import { BrowserMultiFormatReader } from '@zxing/library'

export default {
  props: {
    form: {
      type: Object,
      default: null
    }
  },
  components: {
  },
  async created () {
    this.codeReader.getVideoInputDevices()
      .then((videoInputDevices) => {
        this.videoList = videoInputDevices
        const selectedDeviceId = videoInputDevices[0].deviceId
        this.codeReader.decodeFromInputVideoDeviceContinuously(selectedDeviceId, 'video', (result, err) => {
        })
      })
  },
  data () {
    return {
      codeReader: new BrowserMultiFormatReader(),
      imageUrl: ''
    }
  },
  methods: {
    async handleClick () {
      const canvas = document.createElement('canvas')
      const context = canvas.getContext('2d')

      canvas.width = 400
      canvas.height = 300

      context.drawImage(this.$refs.video, 0, 0, canvas.width, canvas.height)
      this.imageUrl = canvas.toDataURL('image/jpeg')
      this.form.avatar = this.imageUrl
    }
  }
}
</script>

<style scoped lang="less">
.step2-wrapper {
  background: #ffffff;
  height: 100%;
  position: relative;

  .tips {
    height: 80px;
    line-height: 80px;
    text-align: center;
    font-size: 22px;
    color: #003b82;
  }

  .take-photo {
    display: flex;

    .column2 {
      flex: 2;
    }

    .column1 {
      flex: 1;
    }

    .take {
      height: 100px;
      width: 100px;
      position: fixed;
      left: 50%;
      top: 50%;
      margin-top: 0px;
      margin-left: -50px;
      text-align: center;
      border: 1px solid #1090dd;
      border-radius: 50%;
      font-size: 60px;
      color: #ffffff;
      background: #1090dd;
    }
    .photo {
      border: 1px solid #1090dd;
      height: 300px;
    }
    .avatar {
      width: 100%;
      height: 100%;
    }
  }
}
</style>
