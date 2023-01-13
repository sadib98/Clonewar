<template>
    <div id="upload-block" class="row">
        
        <!--Upload Zone-->
        <form method="post" @submit.prevent="postFilePath">
            <div class="row">
                <div class="row">
                    <p class="text-center upload-field-title">
                        <label for="formControlInput" class="form-label">Choose a file (enter file location)</label>
                        <input type="text" class="form-control" id="path" v-model="path" />
                    </p>
                </div>

                <div class="row">
                    <p class="text-center upload-button">
                        <button>Add</button>
                    </p>
                </div>
            </div>
        </form>
    </div>
</template>

<script>

export default {
    name: 'PostFilePath',
    data(){
        return {
            path: ''
        }
    },
    methods: {
        postFilePath(){
            console.log(this.path);
            
            fetch('api/files/upload', {
                method: 'POST',
                body: this.path
            } )
            .then( function( response ){
                if( response.status != 200 ){
                    this.fetchError = response.status;
                }else{
                    response.json().then( function( data ){
                        this.fetchResponse = data;
                    }.bind(this));
                }
            }.bind(this));
            window.location.reload()
        }
    }
    
}
</script>

<style>
#upload-block{
    background-color: rgb(226, 226, 226);
}

.upload-field-title{
    font-size: 20px;
    text-align: center;
}

.upload-button{
    width:200px;
}
</style>
