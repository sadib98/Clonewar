import * as Vue from "vue";
import Header from './Header.vue';
import FileList from './FileList.vue';
import PostFilePath from './UploadFile.vue';


const header = Vue.createApp(Header);
header.mount("#header");

Vue.createApp(FileList).mount("#filelist");

const addfile = Vue.createApp(PostFilePath);
addfile.mount("#addfile");