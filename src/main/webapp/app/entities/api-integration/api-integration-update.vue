<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2 id="thirdPartyIntegrationApplicationApp.apiIntegration.home.createOrEditLabel" data-cy="ApiIntegrationCreateUpdateHeading">
          Create or edit a Api Integration
        </h2>
        <div>
          <div class="form-group" v-if="apiIntegration.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="apiIntegration.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="api-integration-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="api-integration-name"
              data-cy="name"
              :class="{ valid: !v$.name.$invalid, invalid: v$.name.$invalid }"
              v-model="v$.name.$model"
              required
            />
            <div v-if="v$.name.$anyDirty && v$.name.$invalid">
              <small class="form-text text-danger" v-for="error of v$.name.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="api-integration-url">Url</label>
            <input
              type="text"
              class="form-control"
              name="url"
              id="api-integration-url"
              data-cy="url"
              :class="{ valid: !v$.url.$invalid, invalid: v$.url.$invalid }"
              v-model="v$.url.$model"
              required
            />
            <div v-if="v$.url.$anyDirty && v$.url.$invalid">
              <small class="form-text text-danger" v-for="error of v$.url.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="api-integration-type">Type</label>
            <select
              class="form-control"
              name="type"
              :class="{ valid: !v$.type.$invalid, invalid: v$.type.$invalid }"
              v-model="v$.type.$model"
              id="api-integration-type"
              data-cy="type"
              required
            >
              <option v-for="contentType in contentTypeValues" :key="contentType" v-bind:value="contentType">{{ contentType }}</option>
            </select>
            <div v-if="v$.type.$anyDirty && v$.type.$invalid">
              <small class="form-text text-danger" v-for="error of v$.type.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="api-integration-auth">Auth</label>
            <select
              class="form-control"
              name="auth"
              :class="{ valid: !v$.auth.$invalid, invalid: v$.auth.$invalid }"
              v-model="v$.auth.$model"
              id="api-integration-auth"
              data-cy="auth"
              required
            >
              <option v-for="authType in authTypeValues" :key="authType" v-bind:value="authType">{{ authType }}</option>
            </select>
            <div v-if="v$.auth.$anyDirty && v$.auth.$invalid">
              <small class="form-text text-danger" v-for="error of v$.auth.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="api-integration-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="api-integration-description"
              data-cy="description"
              :class="{ valid: !v$.description.$invalid, invalid: v$.description.$invalid }"
              v-model="v$.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="api-integration-version">Version</label>
            <input
              type="text"
              class="form-control"
              name="version"
              id="api-integration-version"
              data-cy="version"
              :class="{ valid: !v$.version.$invalid, invalid: v$.version.$invalid }"
              v-model="v$.version.$model"
            />
            <div v-if="v$.version.$anyDirty && v$.version.$invalid">
              <small class="form-text text-danger" v-for="error of v$.version.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="api-integration-timeout">Timeout</label>
            <input
              type="number"
              class="form-control"
              name="timeout"
              id="api-integration-timeout"
              data-cy="timeout"
              :class="{ valid: !v$.timeout.$invalid, invalid: v$.timeout.$invalid }"
              v-model.number="v$.timeout.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="api-integration-retryRetries">Retry Retries</label>
            <input
              type="number"
              class="form-control"
              name="retryRetries"
              id="api-integration-retryRetries"
              data-cy="retryRetries"
              :class="{ valid: !v$.retryRetries.$invalid, invalid: v$.retryRetries.$invalid }"
              v-model.number="v$.retryRetries.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="api-integration-retryDelay">Retry Delay</label>
            <input
              type="number"
              class="form-control"
              name="retryDelay"
              id="api-integration-retryDelay"
              data-cy="retryDelay"
              :class="{ valid: !v$.retryDelay.$invalid, invalid: v$.retryDelay.$invalid }"
              v-model.number="v$.retryDelay.$model"
            />
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
<script lang="ts" src="./api-integration-update.component.ts"></script>
