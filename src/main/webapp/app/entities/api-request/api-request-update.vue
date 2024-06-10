<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2 id="thirdPartyIntegrationApplicationApp.apiRequest.home.createOrEditLabel" data-cy="ApiRequestCreateUpdateHeading">
          Create or edit a Api Request
        </h2>
        <div>
          <div class="form-group" v-if="apiRequest.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="apiRequest.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="api-request-uri">Uri</label>
            <input
              type="text"
              class="form-control"
              name="uri"
              id="api-request-uri"
              data-cy="uri"
              :class="{ valid: !v$.uri.$invalid, invalid: v$.uri.$invalid }"
              v-model="v$.uri.$model"
              required
            />
            <div v-if="v$.uri.$anyDirty && v$.uri.$invalid">
              <small class="form-text text-danger" v-for="error of v$.uri.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="api-request-body">Body</label>
            <input
              type="text"
              class="form-control"
              name="body"
              id="api-request-body"
              data-cy="body"
              :class="{ valid: !v$.body.$invalid, invalid: v$.body.$invalid }"
              v-model="v$.body.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="api-request-method">Method</label>
            <select
              class="form-control"
              name="method"
              :class="{ valid: !v$.method.$invalid, invalid: v$.method.$invalid }"
              v-model="v$.method.$model"
              id="api-request-method"
              data-cy="method"
              required
            >
              <option v-for="httpMethod in httpMethodValues" :key="httpMethod" v-bind:value="httpMethod">{{ httpMethod }}</option>
            </select>
            <div v-if="v$.method.$anyDirty && v$.method.$invalid">
              <small class="form-text text-danger" v-for="error of v$.method.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./api-request-update.component.ts"></script>
