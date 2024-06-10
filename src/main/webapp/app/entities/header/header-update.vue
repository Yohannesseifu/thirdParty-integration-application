<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2 id="thirdPartyIntegrationApplicationApp.header.home.createOrEditLabel" data-cy="HeaderCreateUpdateHeading">
          Create or edit a Header
        </h2>
        <div>
          <div class="form-group" v-if="header.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="header.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="header-key">Key</label>
            <input
              type="text"
              class="form-control"
              name="key"
              id="header-key"
              data-cy="key"
              :class="{ valid: !v$.key.$invalid, invalid: v$.key.$invalid }"
              v-model="v$.key.$model"
              required
            />
            <div v-if="v$.key.$anyDirty && v$.key.$invalid">
              <small class="form-text text-danger" v-for="error of v$.key.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="header-value">Value</label>
            <input
              type="text"
              class="form-control"
              name="value"
              id="header-value"
              data-cy="value"
              :class="{ valid: !v$.value.$invalid, invalid: v$.value.$invalid }"
              v-model="v$.value.$model"
              required
            />
            <div v-if="v$.value.$anyDirty && v$.value.$invalid">
              <small class="form-text text-danger" v-for="error of v$.value.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="header-apiRequest">Api Request</label>
            <select class="form-control" id="header-apiRequest" data-cy="apiRequest" name="apiRequest" v-model="header.apiRequest">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="header.apiRequest && apiRequestOption.id === header.apiRequest.id ? header.apiRequest : apiRequestOption"
                v-for="apiRequestOption in apiRequests"
                :key="apiRequestOption.id"
              >
                {{ apiRequestOption.id }}
              </option>
            </select>
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
<script lang="ts" src="./header-update.component.ts"></script>
