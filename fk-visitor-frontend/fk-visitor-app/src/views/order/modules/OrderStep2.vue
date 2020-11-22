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
      default: undefined
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
      imageUrl: 'data:image/png;base64,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'
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
      canvas.toBlob(blob => {
        that.$emit('change', blob)
      })
    }
  }
}
</script>

<style scoped>
.step2-wrapper {
  background: #ffffff;
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
  margin-top: -150px;
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
  margin-top: -150px;
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
