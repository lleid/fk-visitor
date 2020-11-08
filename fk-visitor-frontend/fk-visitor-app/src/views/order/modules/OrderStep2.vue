<template>
  <div class="step2-wrapper">
    <div class="video">
      <video ref="video" id="video" width="400"></video>
    </div>
    <div class="take" @click="handleClick">
      <c-icon type="fv-paizhao"></c-icon>
    </div>
    <div class="print">
      <img :src="imageUrl" class="avatar" width="100%" height="100%" v-if="imageUrl" />
    </div>
  </div>
</template>

<script>
// eslint-disable-next-line no-unused-vars
import adapter from 'webrtc-adapter'
import { BrowserMultiFormatReader } from '@zxing/library'

export default {
  props: {
    file: {
      type: Blob,
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

      const that = this
      await canvas.toBlob(blob => {
        that.$emit('change', blob)
      })
    }
  }
}
</script>

<style scoped>
.step2-wrapper {
  height: 100%;
  position: relative;
}

.step2-wrapper .video {
  width: 400px;
  height: 300px;
  position: fixed;
  left: 25%;
  top: 50%;
  margin-left: -150px;
  margin-top: -112px;
}

.step2-wrapper .take {
  height: 100px;
  width: 100px;
  position: fixed;
  left: 50%;
  top: 50%;
  margin-top: -50px;
  margin-left: -50px;
  text-align: center;
  border: 1px solid #1090dd;
  border-radius: 50%;
  font-size: 60px;
  color: #ffffff;
  background: #1090dd;
}

.step2-wrapper .print {
  width: 400px;
  height: 300px;
  position: fixed;
  right: 25%;
  top: 50%;
  margin-right: -150px;
  margin-top: -112px;
  border: 1px solid #1090dd;
}

.step2-wrapper .avatar {
  width: 100%;
  height: 100%;
}

.step2-wrapper .upload-btn {
  display: inline-block;
  height: 50px;
  line-height: 60px;
  font-size: 16px;
  font-weight: bold;
  border-bottom: 1px solid #000;
  width: 100px;
}
</style>
