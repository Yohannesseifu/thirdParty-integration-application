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
            <label class="form-control-label" for="header-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="header-name"
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
            <label class="form-control-label" for="header-valueStr">Value Str</label>
            <input
              type="text"
              class="form-control"
              name="valueStr"
              id="header-valueStr"
              data-cy="valueStr"
              :class="{ valid: !v$.valueStr.$invalid, invalid: v$.valueStr.$invalid }"
              v-model="v$.valueStr.$model"
              required
            />
            <div v-if="v$.valueStr.$anyDirty && v$.valueStr.$invalid">
              <small class="form-text text-danger" v-for="error of v$.valueStr.$errors" :key="error.$uid">{{ error.$message }}</small>
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
